package np_cpu

import chisel3._
import chisel3.util._

class InstMemIO extends Bundle{
  val pc_ptr  = Input (UInt(8.W))
  val opcode  = Output(UInt(6.W))
  val rs_addr = Output(UInt(5.W))
  val rt_addr = Output(UInt(5.W))
  val rd_addr = Output(UInt(5.W))
  val shamt   = Output(UInt(5.W))
  val func    = Output(UInt(6.W))
  val imm_16  = Output(UInt(16.W))
  val imm_26  = Output(UInt(26.W))
}

class InstMem extends Module{
  val io = IO(new InstMemIO())
  
  //ROM
  val mem = VecInit(
  "b00000010".U,               //ADD $s1 $s2 $s3 00000010_01010011_10001000_00100000
  "b01010011".U,
  "b10001000".U,
  "b00100000".U,
  "b00110110".U,               //ORI $s1 $s2  56 00110110_01010001_00000000_00111000
  "b01010001".U,
  "b00000000".U,
  "b00111000".U,
  "b10001110".U,               //LW $s1 100(%s2) 10001110_01010001_00000000_01100100
  "b01010001".U,
  "b00000000".U,
  "b01100100".U,
  "b10101110".U,               //SW $s3  32(%s4) 10101110_10010011_00000000_00100000
  "b10010011".U,
  "b00000000".U,
  "b00100000".U
  )
  
  val inst =  Wire(UInt(32.W))
  inst    :=  Cat(mem(io.pc_ptr),mem(io.pc_ptr + 1.U),mem(io.pc_ptr + 2.U),mem(io.pc_ptr + 3.U))
  
  io.opcode  := inst(31,26)
  io.rs_addr := inst(25,21)
  io.rt_addr := inst(20,16)
  io.rd_addr := inst(15,11)
  io.shamt   := inst(10,6)
  io.func    := inst(5,0)
  io.imm_16  := inst(15,0)
  io.imm_26  := inst(25,0)
  
}

object InstMem extends App{
  (new chisel3.stage.ChiselStage).emitVerilog(new InstMem())
}