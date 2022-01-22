package aias_lab3

import chisel3._
import chisel3.util._

class Stack(val depth: Int) extends Module {
  val io = IO(new Bundle {
    val push    = Input(Bool())
    val pop     = Input(Bool())
    val dataIn  = Input(UInt(32.W))
    val dataOut = Output(UInt(32.W))
    val empty = Output(Bool())
  })

  val stack_mem = Mem(depth, UInt(32.W))
  val sp        = RegInit(0.U(log2Ceil(depth+1).W))

  when(io.push && (sp < depth.asUInt)) {
    stack_mem(sp) := io.dataIn
    sp := sp + 1.U
  } .elsewhen(io.pop && (sp > 0.U)) {
    sp := sp - 1.U
  }

  io.empty := sp === 0.U
  io.dataOut := Mux(!io.empty, stack_mem(sp - 1.U), 0.U)
}
