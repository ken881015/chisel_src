package np_cpu

import chisel3._

class ALU extends Module{
  val io = IO(new Bundle{
    val in1 = Input(UInt(32.W))
	val in2 = Input(UInt(32.W))
	val out = Output(UInt(32.W))
  })
  io.out := 0.U
}

/* verilog generator
object ALU extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new ALU())
}
*/
