package np_cpu

import chisel3._

class PC extends Module{
  val io = IO(new Bundle{
	val out = Output(UInt(32.W))
  })
  
  val PC_reg = RegInit(0.U(32.W))
  
  PC_reg := PC_reg + 4.U
  
  io.out := PC_reg
}