package aias_lab3

import chisel3._
import chisel3.util._
import scala.languageFeature.postfixOps

class ArithmeticCalculator extends Module{
  val io = IO(new Bundle{
    val key_in = Input(UInt(4.W))
    val valid = Output(Bool())
    val Answer = Output(UInt(32.W))
  })

  val stack = Module(new stack(4))
  val inReg = RegInit(0.U(32.W))

  val length = 16
  val postfix = RegInit(VecInit(Seq.fill(length)(0.U(32.W))))
  val pointer = RegInit(0.U(log2Ceil(length).W))
  
  //0~9
  val number = Wire(Bool())
  number := io.key_in < 10.U
  //+,-,x
  val operator = Wire(Bool())
  operator := (io.key_in >= 10.U) || (io.key_in <= 12.U)
  //(,) parentheses
  val parentheses = Wire(Bool())
  parentheses := (io.key_in >= 13.U) || (io.key_in <= 14.U)
  //= equal sign
  val equal = Wire(Bool())
  equal := io.key_in === 15.U
  
  //operand priority
  val priority = WireDefault(false.B)
  when(io.key === 12.U){
    priority := true.B
  }

  when(number){
    inReg := (inReg << 3) + (inReg << 1) + io.key_in
  }.elsewhen(operator){
    //put operand in postfix
    postfix(pointer) := inReg
    pointer := pointer + 1
    inReg := 0
    
    //operand
    when(stack.dataOut(4) > io.key_in(4))
    
    stack.push := true.B
    stack.dataIn := io.key_in
  }.elsewhen(parentheses){

  }.elsewhen(equal){
     
  }

  io.valid := false.B
  io.Answer := 0.U
}

object ACDriver extends App{
  Driver.execute(args,() => new ArithmeticCalculator())
}