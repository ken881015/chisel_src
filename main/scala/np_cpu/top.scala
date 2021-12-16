package np_cpu

import chisel3._
import chisel3.util._
import parameter._

class top extends Module{
  val io = IO(new Bundle{
    //PC Port
	val pc_ptr = Output(UInt(8.W))
  
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
	// val ALUout = Output(UInt(32.W))
	
	//Controller Port
	// val reg_dst  = Output(UInt(32.W))
	
  })
  val PC = Module(new PC())
  val InstMem    = Module(new InstMem())
  val RegFile    = Module(new RegFile())
  val Controller = Module(new Controller())
  val ALU_ctrl   = Module(new ALU_ctrl())
  val ALU        = Module(new ALU())
  val DataMem    = Module(new DataMem())
  
  
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
  RegFile.io.wen   := Controller.io.RegWrite
  RegFile.io.wdata := Mux(Controller.io.MemToReg.asBool,DataMem.io.rdata,ALU.io.ALUout)
  RegFile.io.waddr := Mux(Controller.io.RegDst.asBool,InstMem.io.rd_addr,InstMem.io.rt_addr)
  
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
  // io.ALUout := ALU.io.ALUout
  
  //DataMem Port
  DataMem.io.wen   := Controller.io.MemWrite
  DataMem.io.waddr := ALU.io.ALUout
  DataMem.io.wdata := RegFile.io.rdata2
  DataMem.io.ren   := Controller.io.MemRead
  DataMem.io.raddr := ALU.io.ALUout
  
  //Branch Part
  val branch_taken = Wire(Bool())
  branch_taken := ( ALU.io.ALUzero && Controller.io.opcode === op_BEQ) ||
                  (!ALU.io.ALUzero && Controller.io.opcode === op_BNE)
				  
  //Jump
  
  //PC
  PC.io.pc_in := PC.io.pc_ptr + 4.U
  when (Controller.io.Jump.asBool){
    PC.io.pc_in := (InstMem.io.imm_26 << 2)(7,0)
  }.elsewhen(branch_taken){
    PC.io.pc_in := PC.io.pc_ptr + 4.U + (InstMem.io.imm_16 << 2)(7,0)
  }
  io.pc_ptr := PC.io.pc_ptr
}

// object top extends App{
//   //(new chisel3.stage.ChiselStage).emitVerilog(new top())
//   chisel3.Driver.execute(args, () => new top())
// }