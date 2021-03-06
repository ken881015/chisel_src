package np_cpu

import chisel3._

/*
class PC extends Module{
  val io = IO(new Bundle{
	val pc_ptr = Output(UInt(8.W))
  })
  
  val PC_reg = RegInit(0.U(8.W))
  
  PC_reg := PC_reg + 4.U
  
  io.pc_ptr := PC_reg
}
*/

class PCIO extends Bundle{
  val pc_in  = Input (UInt(8.W))
  val pc_ptr = Output(UInt(8.W))
}

class PC extends Module{
  val io = IO(new PCIO())
  
  val PC_reg = RegInit(0.U(8.W))
  PC_reg := io.pc_in
  //Deside at top.scala
  //PC_reg := PC_reg + 4.U
  io.pc_ptr := PC_reg
}