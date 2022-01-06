package aias_lab3

import chisel3._
import chisel3.util._

class Stack(val depth: Int) extends Module {
  val io = IO(new Bundle {
    val push    = Input(Bool())
    val pop     = Input(Bool())
    val dataIn  = Input(UInt(8.W))
    val dataOut = Output(UInt(8.W))
    val empty = Output(Bool())
  })

  val stack_mem = Mem(depth, UInt(8.W))
  val sp        = RegInit(0.U(log2Ceil(depth+1).W))
  val out       = RegInit(0.U(8.W))


  when(io.push && (sp < depth.asUInt)) {
    stack_mem(sp) := io.dataIn
    sp := sp + 1.U
  } .elsewhen(io.pop && (sp > 0.U)) {
    sp := sp - 1.U
  }
  when (sp > 0.U) {
    out := stack_mem(sp - 1.U)
  }


  io.dataOut := out
  io.empty := sp === 0.U
}
