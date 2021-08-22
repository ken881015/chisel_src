package np_cpu

import chisel3._

class ALUIO extends Bundle{
  val src1 = Input(UInt(32.W))
  val src2 = Input(UInt(32.W))
  val out  = Output(UInt(32.W))
}

/*
object ALU {
  val ALU_ADD    = 0.U(4.W)
  val ALU_SUB    = 1.U(4.W)
  val ALU_AND    = 2.U(4.W)
  val ALU_OR     = 3.U(4.W)
  val ALU_XOR    = 4.U(4.W)
  val ALU_SLT    = 5.U(4.W)
  val ALU_SLL    = 6.U(4.W)
  val ALU_SLTU   = 7.U(4.W)
  val ALU_SRL    = 8.U(4.W)
  val ALU_SRA    = 9.U(4.W)
  val ALU_COPY_A = 10.U(4.W)
  val ALU_COPY_B = 11.U(4.W)
  val ALU_XXX    = 15.U(4.W)
}
*/


class ALU extends Module{
  val io = IO(new ALUIO)
  io.out := 0.U
}


// verilog generator
// object ALU extends App {
  // (new chisel3.stage.ChiselStage).emitVerilog(new ALU())
// }

