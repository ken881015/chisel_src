package np_cpu

import chisel3._

class ALU_ctrlIO extends Bundle{
  val ALUOp    = Input(UInt(3.W))
  val func     = Input(UInt(6.W))
  val ALUCtrl  = Output(UInt(4.W))
}

// object ALU_ctrl extends App{  
//   (new chisel3.stage.ChiselStage).emitVerilog(new ALU_ctrl())
// }

import parameter._

class ALU_ctrl extends Module{
  val io = IO(new ALU_ctrlIO())
  
  io.ALUCtrl := 0.U
  
  when(io.ALUOp === 0.U){ //LOAD and STORE
    io.ALUCtrl := ctrl_ADD
  }.elsewhen(io.ALUOp === 1.U){
    io.ALUCtrl := ctrl_SUB
  }.elsewhen(io.ALUOp === 2.U){ //R-type
    when(io.func === func_ADD){
	  io.ALUCtrl := ctrl_ADD
	}.elsewhen(io.func === func_SUB){
	  io.ALUCtrl := ctrl_SUB
	}.elsewhen(io.func === func_AND){
	  io.ALUCtrl := ctrl_AND
	}.elsewhen(io.func === func_OR){
	  io.ALUCtrl := ctrl_OR
	}.elsewhen(io.func === func_XOR){
	  io.ALUCtrl := ctrl_XOR
	}.elsewhen(io.func === func_SLL){
	  io.ALUCtrl := ctrl_SLL
	}.elsewhen(io.func === func_SRL){
	  io.ALUCtrl := ctrl_SRL
	}.elsewhen(io.func === func_SRA){
	  io.ALUCtrl := ctrl_SRA
	}
  }.elsewhen(io.ALUOp === 3.U){
    io.ALUCtrl := ctrl_ADD  
  }.elsewhen(io.ALUOp === 5.U){
    io.ALUCtrl := ctrl_AND  
  }.elsewhen(io.ALUOp === 6.U){
    io.ALUCtrl := ctrl_OR  
  }.elsewhen(io.ALUOp === 7.U){
    io.ALUCtrl := ctrl_XOR 
  }
}