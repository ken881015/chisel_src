package np_cpu

import chisel3._

class top extends Module{
  val io = IO(new Bundle{
    val out = Output(UInt(32.W))
  })
  val PC = Module(new PC())
  val InstMem = Module(new InstMem())
  
  InstMem.io.pc_ptr := PC.io.pc_ptr
  
  io.out := InstMem.io.inst

}

object top extends App{
  (new chisel3.stage.ChiselStage).emitVerilog(new top())
}