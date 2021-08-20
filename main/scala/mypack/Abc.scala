package mypack

import chisel3._

class Abc extends Module {
  val io = IO(new Bundle{
    val a = Input(UInt(8.W))
	val b = Input(UInt(8.W))
	val out = Output(UInt(8.W))
  })
  
  io.out :=  io.a + io.b
}

/*
object Abc extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new Abc())
}
*/