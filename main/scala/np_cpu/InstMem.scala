package np_cpu

import chisel3._
import chisel3.util._

class InstMem extends Module{
  val io = IO(new Bundle{
    val pc = Input(UInt(32.W))
	val inst = Output(UInt(32.W))
	})
  val inst_mem = Mem(256, UInt(8.W))
  
  io.inst := Cat(inst_mem(io.pc      ) , inst_mem(io.pc + 1.U) 
               , inst_mem(io.pc + 2.U) , inst_mem(io.pc + 3.U))
}