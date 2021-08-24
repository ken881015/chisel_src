package np_cpu

import chisel3._
import chisel3.util._

class InstMem extends Module{
  val io = IO(new Bundle{
    val pc_ptr = Input (UInt(8.W))
	val opcode = Output(UInt(6.W))
	val rs     = Output(UInt(5.W))
	val rt     = Output(UInt(5.W))
	val rd     = Output(UInt(5.W))
	val shamt  = Output(UInt(5.W))
	val func   = Output(UInt(6.W))
	val imm_16 = Output(UInt(16.W))
	val imm_26 = Output(UInt(26.W))
	})
  
  val mem = VecInit(
  "b00000001".U,
  "b00000010".U,
  "b01000000".U,
  "b01000001".U,
  "b01010101".U,
  "b01010101".U,
  "b00010101".U,
  "b01000001".U,
  "b01000001".U,
  "b01010101".U,
  "b01000001".U,
  "b00010101".U
  )
  
  val test = (mem.size >> 2).asUInt(8.W)
  //println("test = " + test)
  
  val inst := Cat(mem(io.pc_ptr%test      )           , mem(io.pc_ptr%test + 1.asUInt(8.W)) 
                , mem(io.pc_ptr%test + 2.asUInt(8.W)) , mem(io.pc_ptr%test + 3.asUInt(8.W)))
				
  cat(io.opcode,io.rs,io.rt,io.rd,io.shamt,io.func) := inst
  io.imm_16 := cat(io.rd,io.shamt,io.func)
  io.imm_16 := cat(io.rs,io.rt,io.rd,io.shamt,io.func)
  
}

object InstMem extends App{
  (new chisel3.stage.ChiselStage).emitVerilog(new InstMem())
}