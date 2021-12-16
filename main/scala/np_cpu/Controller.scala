package np_cpu

import chisel3._
import chisel3.util._
import parameter._

class ControllerIO extends Bundle{
  val opcode   = Input(UInt(6.W))
  val Jump     = Output(UInt(1.W))
  val Branch   = Output(UInt(1.W))
  val RegDst   = Output(UInt(1.W))
  val RegWrite = Output(UInt(1.W))
  val ALUSrc   = Output(UInt(1.W))
  val ALUOp    = Output(UInt(3.W))
  val MemWrite = Output(UInt(1.W))
  val MemRead  = Output(UInt(1.W))
  val MemToReg = Output(UInt(1.W))
}

class Controller extends Module{
  val io = IO(new ControllerIO())
  
  io.Jump := MuxLookup(io.opcode,0.U,Seq(
    op_JMP -> 1.U
  ))
  
  io.Branch := MuxLookup(io.opcode,0.U,Seq(
    op_BEQ -> 1.U,
	op_BNE -> 1.U
  ))
  
  io.RegDst := MuxLookup(io.opcode,0.U,Seq(
    op_Rtype -> 1.U
  ))
  
  io.RegWrite := MuxLookup(io.opcode,0.U,Seq(
    op_Rtype -> 1.U,
	op_LOAD  -> 1.U,
	op_ADDI  -> 1.U,
	op_ANDI  -> 1.U,
	op_ORI   -> 1.U,
	op_XORI  -> 1.U,
  ))
  
  io.ALUSrc := MuxLookup(io.opcode,0.U,Seq(
    op_Rtype -> 0.U,
	op_LOAD  -> 1.U,
	op_STORE -> 1.U,
	op_ADDI  -> 1.U,
	op_ANDI  -> 1.U,
	op_ORI   -> 1.U,
	op_XORI  -> 1.U,
	op_BEQ   -> 0.U,
	op_BNE   -> 0.U 
  ))
  
  io.ALUOp := MuxLookup(io.opcode,0.U,Seq(
    op_Rtype -> 2.U,
	op_LOAD  -> 0.U,
	op_STORE -> 0.U,
	op_ADDI  -> 3.U,
	op_ANDI  -> 5.U,
	op_ORI   -> 6.U,
	op_XORI  -> 7.U,
	op_BEQ   -> 1.U,
	op_BNE   -> 1.U
  ))
  
  io.MemWrite := MuxLookup(io.opcode,0.U,Seq(
	op_STORE -> 1.U
  ))
  
  io.MemRead := MuxLookup(io.opcode,0.U,Seq(
	op_LOAD -> 1.U
  ))
  
  io.MemToReg := MuxLookup(io.opcode,0.U,Seq(
	op_LOAD -> 1.U
  ))
}

// object Controller extends App{
//   (new chisel3.stage.ChiselStage).emitVerilog(new Controller())
// }