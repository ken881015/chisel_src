package np_cpu

import chisel3._
import chisel3.util._

class top extends Module{
  val io = IO(new Bundle{
    //InstMem Output Port
    // val opcode  = Output(UInt(6.W))
    // val rs_addr = Output(UInt(5.W))
    // val rt_addr = Output(UInt(5.W))
    // val rd_addr = Output(UInt(5.W))
    // val shamt   = Output(UInt(5.W))
    // val func    = Output(UInt(6.W))
    // val imm_16  = Output(UInt(16.W))
    // val imm_26  = Output(UInt(26.W))
	
	//RegFile Output Port
	// val rdata1 = Output(UInt(32.W))
    // val rdata2 = Output(UInt(32.W))
	
	//ALU Input Port
	// val alu_src2 = Output(UInt(32.W))
	
	//ALU_ctrl Output Port
	// val ALUCtrl  = Output(UInt(4.W))
	
	//ALU Output Port
	val ALUout = Output(UInt(32.W))
	
	// val reg_dst  = Output(UInt(32.W))
	
  })
  val PC = Module(new PC())
  val InstMem    = Module(new InstMem())
  val RegFile    = Module(new RegFile())
  val Controller = Module(new Controller())
  val ALU_ctrl   = Module(new ALU_ctrl())
  val ALU        = Module(new ALU())
  
  
  //InstMem Port
  InstMem.io.pc_ptr := PC.io.pc_ptr
  // io.opcode  := InstMem.io.opcode
  // io.rs_addr := InstMem.io.rs_addr
  // io.rt_addr := InstMem.io.rt_addr
  // io.rd_addr := InstMem.io.rd_addr
  // io.shamt   := InstMem.io.shamt
  // io.func    := InstMem.io.func
  // io.imm_16  := InstMem.io.imm_16
  // io.imm_26  := InstMem.io.imm_26
  
  //RegFile Port
  RegFile.io.raddr1 := InstMem.io.rs_addr
  RegFile.io.raddr2 := InstMem.io.rt_addr
  //Undone
  RegFile.io.wen   := 0.U
  RegFile.io.wdata := 0.U
  RegFile.io.waddr := 0.U
  
  //Controller Port
  Controller.io.opcode := InstMem.io.opcode
  
  //ALU_ctrl Port
  ALU_ctrl.io.ALUOp := Controller.io.ALUOp
  ALU_ctrl.io.func  := InstMem.io.func
  // io.ALUCtrl := ALU_ctrl.io.ALUCtrl
  
  //ALU Port
    //Sign extension
  val imm_16_sign_extend = Wire(UInt(32.W))
  imm_16_sign_extend := Cat(Fill(16,InstMem.io.imm_16(15)),InstMem.io.imm_16)
  
  val alu_src2 = Wire(UInt(32.W))
  alu_src2       := Mux(Controller.io.ALUSrc.asBool(),imm_16_sign_extend,RegFile.io.rdata2)
  
  ALU.io.src1    := RegFile.io.rdata1
  ALU.io.src2    := alu_src2
  ALU.io.shamt   := InstMem.io.shamt
  ALU.io.ALUCtrl := ALU_ctrl.io.ALUCtrl
  io.ALUout := ALU.io.ALUout
  
  
  
  
  
  
  
  // io.reg_dst  := Mux(Controller.io.RegDst.asBool(),InstMem.io.rd_addr,InstMem.io.rt_addr)
}

object top extends App{
  (new chisel3.stage.ChiselStage).emitVerilog(new top())
}