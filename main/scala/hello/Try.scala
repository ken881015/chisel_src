package hello

import chisel3._
import chisel3.util._

class TryIO extends Bundle {
  val out = Output(UInt(32.W))
}

class Try extends Module {
  val io = IO(new TryIO())
  
  val Inst_MEM = VecInit(
  "b101001101010101".asUInt(32.W),
  "b101010101010101".asUInt(32.W),
  "b101010101010101".asUInt(32.W),
  "b101010101010101".asUInt(32.W))
  
  //val x_seq = Seq(0.U(5.W),2.U(5.W)) ++ Seq(3.U(5.W),6.U(5.W))
  
  io.out := Inst_MEM(0)
}

object Try extends App{
  (new chisel3.stage.ChiselStage).emitVerilog(new Try())
}

/*
object Filter extends App{
  (new chisel3.stage.ChiselStage).emitVerilog(new Filter(new Plink()))
}
*/