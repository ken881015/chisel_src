package np_cpu

import chisel3._


class RegFileIO extends Bundle {
  val raddr1 = Input(UInt(5.W))
  val raddr2 = Input(UInt(5.W))
  val rdata1 = Output(UInt(32.W))
  val rdata2 = Output(UInt(32.W))
  
  val wen    = Input(UInt(1.W))
  val waddr  = Input(UInt(5.W))
  val wdata  = Input(UInt(32.W))
}

class RegFile extends Module {
  val io = IO(new RegFileIO)
  //val regs = Mem(32, UInt(32.W)) //How about Vec?
  val regs = RegInit(VecInit(Seq.fill(32 - 1)(0.U(32.W))))
  
  io.rdata1 := regs(io.raddr1)
  io.rdata2 := regs(io.raddr2)
  when(io.wen === 1.U && io.waddr =/= 0.U){
    regs(io.waddr) := io.wdata
  }
}

// verilog generator
// object RegFile extends App {
 // (new chisel3.stage.ChiselStage).emitVerilog(new RegFile())
// }
