package np_cpu

import chisel3._
import chisel3.util._

class ControllerIO extends Bundle{
  val opcode   = Input(UInt(6.W))
  val RegDst   = Output(UInt(1.W))
  val RegWrite = Output(UInt(1.W))
  val ALUSrc   = Output(UInt(1.W))
  val ALUOp    = Output(UInt(3.W))
  val MemWrite = Output(UInt(1.W))
  val MemRead  = Output(UInt(1.W))
  val MemToReg = Output(UInt(1.W))
  //val RegDst = Output(UInt(1.W))
}

class Controller extends Module{
  val io = IO(new ControllerIO())
  
  
  io.RegDst := MuxLookup(io.opcode,0.U,Seq(
    "b000000".U -> 1.U  //R-type
  ))
  
  io.RegWrite := MuxLookup(io.opcode,0.U,Seq(
    "b000000".U -> 1.U,  //R-type
	"b100011".U -> 1.U,  //LOAD
	"b101011".U -> 0.U,  //STORE
	"b001000".U -> 1.U,  //ADDI
	"b001100".U -> 1.U,  //ANDI
	"b001101".U -> 1.U,  //ORI
	"b001110".U -> 1.U  //XORI
  ))
  
  io.ALUSrc := MuxLookup(io.opcode,0.U,Seq(
    "b000000".U -> 0.U,  //R-type
	"b100011".U -> 1.U,  //LOAD
	"b101011".U -> 1.U,  //STORE
	"b001000".U -> 1.U,  //ADDI
	"b001100".U -> 1.U,  //ANDI
	"b001101".U -> 1.U,  //ORI
	"b001110".U -> 1.U  //XORI
  ))
  
  io.ALUOp := MuxLookup(io.opcode,0.U,Seq(
    "b000000".U -> 2.U,  //R-type
	"b100011".U -> 0.U,  //LOAD
	"b101011".U -> 0.U,  //STORE
	"b001000".U -> 3.U,  //ADDI
	"b001100".U -> 5.U,  //ANDI
	"b001101".U -> 6.U,  //ORI
	"b001110".U -> 7.U  //XORI
  ))
  
  io.MemWrite := MuxLookup(io.opcode,0.U,Seq(
	"b101011".U -> 1.U  //STORE
  ))
  
  io.MemRead := MuxLookup(io.opcode,0.U,Seq(
	"b100011".U -> 1.U  //LOAD
  ))
  
  io.MemToReg := MuxLookup(io.opcode,0.U,Seq(
	"b100011".U -> 1.U  //LOAD
  ))
}

object Controller extends App{
  (new chisel3.stage.ChiselStage).emitVerilog(new Controller())
}