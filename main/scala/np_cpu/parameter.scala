package np_cpu

import chisel3._

class parameter {

}

object parameter{
  
  val op_Rtype = "b000000".U
  val op_LOAD  = "b100011".U
  val op_STORE = "b101011".U
  val op_ADDI  = "b001000".U
  val op_ANDI  = "b001100".U
  val op_ORI   = "b001101".U
  val op_XORI  = "b001110".U
  val op_BEQ   = "b000100".U
  val op_BNE   = "b000101".U
  val op_JMP   = "b000010".U
  
  val func_ADD  = 32.U(6.W)
  val func_SUB  = 34.U(6.W)
  val func_AND  = 36.U(6.W)
  val func_OR   = 37.U(6.W)
  val func_XOR  = 38.U(6.W)
  val func_SLL  = 0.U (6.W)
  val func_SRL  = 2.U (6.W)
  val func_SRA  = 3.U (6.W)
  
  val ctrl_ADD  = 11.U(4.W)
  val ctrl_SUB  = 12.U(4.W)
  val ctrl_AND  = 2.U (4.W)
  val ctrl_OR   = 3.U (4.W)
  val ctrl_XOR  = 6.U (4.W)
  val ctrl_SLL  = 4.U (4.W)
  val ctrl_SRL  = 5.U (4.W)
  val ctrl_SRA  = 8.U (4.W)
}