package aias_lab3

import chisel3._
import chisel3.util._
import scala.languageFeature.postfixOps
import scala.annotation.switch

class ArithmeticCalculator extends Module{
  val io = IO(new Bundle{
    val key_in = Input(UInt(4.W))
    val valid = Output(Bool())
    val Answer = Output(UInt(32.W))
    val RegCheck = Output(Vec(32,UInt(32.W)))
  })
  
  //stack declaration
  val stack = Module(new Stack(4))
  //default
  stack.io.push := false.B
  stack.io.pop := false.B
  stack.io.dataIn := 0.U

  val inReg = RegInit(0.U(32.W))

  val length = 32
  val postfix = RegInit(VecInit(Seq.fill(length)(0.U(32.W))))
  val is_num = RegInit(VecInit(Seq.fill(length)(false.B)))
  val ptr = RegInit(0.U(log2Ceil(length).W))
  
  val cnt = RegInit(0.U(4.W))
  cnt := Mux(cnt === 9.U, 0.U , cnt + 1.U)
  val tick = WireDefault(false.B)
  tick := cnt === 1.U

  
  // //0~9
  val number = Wire(Bool())
  number := io.key_in < 10.U
  // //+,-,x
  val operator = Wire(Bool())
  operator := (io.key_in >= 10.U) || (io.key_in <= 12.U)
  // //(,) parentheses
  val parentheses = Wire(Bool())
  parentheses := (io.key_in >= 13.U) || (io.key_in <= 14.U)
  //= equal sign
  val equal = Wire(Bool())
  equal := io.key_in === 15.U
  
  
  // operand Precedence
  def op_precedence (in:UInt):UInt{
    
  }

  when(tick){
    when(number){
      inReg := (inReg << 3) + (inReg << 1) + io.key_in
    }.otherwise{
      postfix(ptr) := inReg
      is_num(ptr) := true.B
      ptr := ptr + 1.U
      inReg := 0.U
    }

    when(operator || parentheses){
      stack.io.push := true.B
      stack.io.dataIn := io.key_in
    }.elsewhen(equal){

    }
  }
  
    

  io.valid := false.B
  io.Answer := 0.U

  for(i <- 0 until length){
    io.RegCheck(i) := postfix(i)
  }
}

object ACDriver extends App{
  Driver.execute(args,() => new ArithmeticCalculator())
}