package aias_lab3

import chisel3._
import chisel3.util._
import chisel3.experimental.ChiselEnum
import scala.annotation.switch

class ArithmeticCalculator_v2 extends Module{

  val length = 32

  val io = IO(new Bundle{
    val key_in = Input(UInt(4.W))
    val ready = Output(Bool())
    val valid = Output(Bool())
    val Answer = Output(UInt(32.W))
    // val RegCheck = Output(Vec(length,UInt(32.W)))
    // val NumCheck = Output(Vec(length,Bool()))
  })

  //stack declaration
  val stack = Module(new Stack(4))
  //default
  stack.io.push := false.B
  stack.io.pop := false.B
  stack.io.dataIn := 0.U

  val inReg = RegInit(0.U(32.W))
  val numReg = RegInit(false.B)
  
  val form_ptr = RegInit(0.U(log2Ceil(length).W))
  val post_ptr = RegInit(0.U(log2Ceil(length).W))
  val formulaReg = RegInit(VecInit(Seq.fill(length)(0.U(32.W))))
  val postReg = RegInit(VecInit(Seq.fill(length)(0.U(32.W))))
  val itemReg = RegInit(VecInit(Seq.fill(length)(false.B)))
  
  val firstReg = RegInit(false.B)
  val lastReg = RegInit(false.B) //true:number
  val BTStackReg = RegInit(false.B) //back to stack
 
  // //0~9
  val number = Wire(Bool())
  number := io.key_in < 10.U
  // //+,-,x
  val operator = Wire(Bool())
  operator := (io.key_in >= 10.U) && (io.key_in <= 12.U)
  // //(,) parentheses
  val parentheses = Wire(Bool())
  parentheses := (io.key_in >= 13.U) && (io.key_in <= 14.U)
  //= equal sign
  val equal = Wire(Bool())
  equal := io.key_in === 15.U

  def op_precedence(input:UInt):UInt={
    val precedence = Mux(input === 12.U,1.U,0.U)
    precedence
  }

  def alu (in1:UInt,in2:UInt,op:UInt) : UInt = {
    val out = Wire(UInt(32.W))
    when(op === 10.U){
      out := in1 + in2
    }.elsewhen(op === 11.U){
      out := in1 - in2
    }.elsewhen(op === 12.U){
      out := in1 * in2
    }.otherwise{
      out := 0.U
    }
    out
  }

  val sIdle :: sAccept :: sPost :: sCalc :: sFinish :: Nil = Enum(5)
  val state = RegInit(sIdle)

  io.ready := false.B
  switch(state){
    //Default statement
    is(sIdle){state := sAccept}

    //purpose: input data preprocess
    is(sAccept){
      //Hand-Shake
      io.ready := true.B
      state := sAccept
      firstReg := true.B
      lastReg := Mux(number,true.B,false.B)
      formulaReg(form_ptr) := inReg
      itemReg(form_ptr) := numReg
      numReg := Mux(number,true.B,false.B)

      when(number){
        form_ptr := Mux(lastReg,0.U,1.U) + form_ptr
        // form_ptr := Mux((form_ptr=/=0),Mux(lastReg,0.U,1.U),0.U) + form_ptr
        // form_ptr := Mux(((form_ptr =/= 0.U)&&(lastReg)),1.U,0.U) + form_ptr

        when(!firstReg){
          form_ptr := form_ptr
        }

        inReg := Mux(lastReg, (inReg << 3) + (inReg << 1), 0.U) + io.key_in
      }
      .elsewhen(operator || parentheses){
        form_ptr := Mux(!firstReg, 0.U, 1.U) + form_ptr
        inReg := io.key_in
      }
      .elsewhen(equal){
        inReg := 0.U
        state := sPost
      }
    }

    // infix -> postfix
    is(sPost){
      state := sPost
      itemReg(post_ptr) := Mux(itemReg(inReg),true.B,false.B)
      //Reuse the inReg cuz the sAccept is end!

       //when formula(i) is number => DIRECTLY STORE FOR POSTFIX
      when(itemReg(inReg)){
        postReg(post_ptr) := formulaReg(inReg)
        inReg := inReg + 1.U
        post_ptr := post_ptr + 1.U
      }.otherwise{
        //when formula(i) is operator -> precedence check in the stack
        when(formulaReg(inReg)>= 10.U && formulaReg(inReg)<=12.U){

          when(stack.io.empty){
            stack.io.push := true.B
            stack.io.dataIn := formulaReg(inReg)
            inReg := inReg + 1.U
          }.otherwise{
            when(op_precedence(stack.io.dataOut) > op_precedence(formulaReg(inReg))){
              stack.io.pop := true.B
              postReg(post_ptr) := stack.io.dataOut
              post_ptr := post_ptr + 1.U
            }.otherwise{
              stack.io.push := true.B
              stack.io.dataIn := formulaReg(inReg)
              inReg := inReg + 1.U
            }
          }
        //when formula(i) is left_parent -> push in stack
        }.elsewhen(formulaReg(inReg) === 13.U){
          stack.io.push := true.B
          stack.io.dataIn := formulaReg(inReg)
          inReg := inReg + 1.U
        }
        //when formula(i) is right_parent -> stack pop until meet his partner
        .elsewhen(formulaReg(inReg) === 14.U){
          when(stack.io.dataOut =/= 13.U){
            stack.io.pop := true.B
            postReg(post_ptr) := stack.io.dataOut
            post_ptr := post_ptr + 1.U
          }.otherwise{
            stack.io.pop := true.B
            inReg := inReg + 1.U
          }
        }
        //when formula(i) is equal -> pop until stack is empty 
        .elsewhen(inReg > form_ptr){
          when(!stack.io.empty){
            stack.io.pop := true.B
            postReg(post_ptr) := stack.io.dataOut
            post_ptr := post_ptr + 1.U
          }
        }
      }
      when(stack.io.empty && inReg > form_ptr){
        inReg := 0.U
        state := sCalc
      }
    }

    is(sCalc){
      state := sCalc
      
      when(inReg < post_ptr){
        // put the number in to stack
        when(itemReg(inReg)){
          stack.io.push := true.B
          stack.io.dataIn := postReg(inReg)
          inReg := inReg + 1.U
        }.otherwise{
          // pop out the 2 number for calculation when postReg(i) == operator
          when(!BTStackReg){
            //store the first pop out number
            //calculate the outcome when the second operand is read
            stack.io.pop := true.B
            lastReg := !lastReg
            formulaReg(0) := Mux(lastReg,
                                alu(stack.io.dataOut,formulaReg(0),postReg(inReg)),
                                stack.io.dataOut)
            BTStackReg := Mux(lastReg,!BTStackReg,BTStackReg)

          }.otherwise{
            inReg := inReg + 1.U
            stack.io.push := true.B
            stack.io.dataIn := formulaReg(0)
            BTStackReg := !BTStackReg
          }
        }
      }.otherwise{
        state := sFinish
      }
    }

    is(sFinish){
      
    }
  }

  io.valid := Mux(state===sFinish,true.B,false.B)
  io.Answer := formulaReg(0)

  // for(i <- 0 until length){
  //   io.RegCheck(i) := postReg(i)
  //   io.NumCheck(i) := itemReg(i)
  // }
}