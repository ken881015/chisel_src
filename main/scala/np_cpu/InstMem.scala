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
  "b00100000".U,               // 0: ADDI $s2 $zero 1   00100000_00010010_00000000_00000001
  "b00010010".U,
  "b00000000".U,
  "b00000001".U,
  "b00100000".U,               // 4: ADDI $s3 $zero 10  00100000_00010011_00000000_00000011
  "b00010011".U,
  "b00000000".U,
  "b00000011".U,
  "b00010001".U,               // 8: BEQ  $t1 $S3   7   00010001_00110011_00000000_00000111
  "b00110011".U,
  "b00000000".U,
  "b00000111".U,
  "b00000010".U,               //12: ADD  $s4 $s1 $s2   00000010_00110010_10100000_00100000
  "b00110010".U,
  "b10100000".U,
  "b00100000".U,
  "b00000001".U,               //16: SLL  $t4 $t1   2   00000001_00100000_01100000_10000000
  "b00100000".U,
  "b01100000".U,
  "b10000000".U,
  "b10101101".U,               //20: SW   $s4 0($t4)    10101101_10010100_00000000_00000000
  "b10010100".U,
  "b00000000".U,
  "b00000000".U,
  "b00000000".U,               //24: ADD  $s1 $zero $s2 00000000_00010010_10001000_00100000
  "b00010010".U,
  "b10001000".U,
  "b00100000".U,
  "b00000000".U,               //28: ADD  $s2 $zero $s4 00000000_00010100_10010000_00100000
  "b00010100".U,
  "b10010000".U,
  "b00100000".U,
  "b00100001".U,               //32: ADDI $t1 $t1   1   00100001_00101001_00000000_00000001
  "b00101001".U,
  "b00000000".U,
  "b00000001".U,
  "b00001000".U,               //36: JMP 2              00001000_00000000_00000000_00000010
  "b00000000".U,
  "b00000000".U,
  "b00000010".U,
  "b00000000".U,               //40: NOP                00000000_00000000_00000000_00000000
  "b00000000".U,
  "b00000000".U,
  "b00000000".U,
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

// object InstMem extends App{
//   (new chisel3.stage.ChiselStage).emitVerilog(new InstMem())
// }