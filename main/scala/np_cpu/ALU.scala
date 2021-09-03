package np_cpu

import chisel3._
import chisel3.util._

class ALUIO extends Bundle{
  val src1    = Input(UInt(32.W))
  val src2    = Input(UInt(32.W))
  val shamt   = Input(UInt(5.W))
  val ALUCtrl = Input(UInt(4.W))
  val ALUout  = Output(UInt(32.W))
  val ALUzero = Output(Bool())
}

import parameter._

class ALU extends Module{
  val io = IO(new ALUIO)
  
  io.ALUout := MuxLookup(io.ALUCtrl,0.U,Seq(
    ctrl_ADD -> (io.src1 + io.src2)   ,
    ctrl_SUB -> (io.src1 - io.src2)   ,
    ctrl_AND -> (io.src1 & io.src2)   ,
    ctrl_OR  -> (io.src1 | io.src2)   ,
    ctrl_XOR -> (io.src1 ^ io.src2)   ,
    ctrl_SLL -> (io.src1 << io.shamt) ,
    ctrl_SRL -> (io.src1 >> io.shamt) ,
    ctrl_SRA -> (io.src1.asSInt >> io.shamt).asUInt
  ))

  io.ALUzero := false.B
  when(io.ALUCtrl === ctrl_SUB){
	io.ALUzero := Mux(io.src1 === io.src2 , true.B, false.B)
  }
}


// verilog generator
object ALU extends App {
  (new chisel3.stage.ChiselStage).emitVerilog(new ALU())
}

