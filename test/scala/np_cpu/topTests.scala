package np_cpu

import chisel3.iotesters._

class topTests (top : top) extends PeekPokeTester(top) {
  
  /*println("SRA  $31 $31   1")
  println("===========================")
  step(1)
  println("ADDI $s2 $zero 1")
  println("===========================")
  step(1)
  println("ADDI $s3 $zero 5")
  println("===========================")
  step(1)
  println("BEQ  $t0 $S3   7")
  println("===========================")
  step(1)
  println("ADD  $s4 $s1 $s2")
  println("===========================")
  step(1)
  println("SLL  $t4 $t1   2 ")
  println("===========================")
  step(1)
  println("SW   $s4 0($t4)")
  println("===========================")
  step(1)
  println("ADD  $s1 $zero $s2")
  println("===========================")
  step(1)
  println("ADD  $s2 $zero $s4")
  println("===========================")
  step(1)
  println("ADDI $t1 $t1   1")
  println("===========================")
  step(1)
  */
  for(i <- 0 until 30){
    println("===========================")
	println("pc = " + peek(top.io.pc_ptr).toString)
    step(1)
  }
}

object topTests extends App {
  /*
  Driver(() => new top()) {top =>
    new topTests(top)
  }
  */
  chisel3.iotesters.Driver.execute(args, () => new top())(c => new topTests(c))
}


// println("opcode = " + peek(top.io.opcode).toString)
// println("rs_addr = " + peek(top.io.rs).toString)
// println("rt_addr = " + peek(top.io.rt).toString)
// println("rd_addr = " + peek(top.io.rd).toString)
// println("shamt = " + peek(top.io.shamt).toString)
// println("func = " + peek(top.io.func).toString)
// println("imm_16 = " + peek(top.io.imm_16).toString)
// println("imm_26 = " + peek(top.io.imm_26).toString)
// println("rdata1 = " + peek(top.io.rdata1).toString)
// println("rdata2 = " + peek(top.io.rdata2).toString)
// println("alu_src2 = " + peek(top.io.alu_src2).toString)
// println("reg_dst = " + peek(top.io.reg_dst).toString)
// println("ALUCtrl = " + peek(top.io.ALUCtrl).toString)
// println("ALUout = " + peek(top.io.ALUout).toString)
// println("pc = " + peek(top.io.pc_ptr).toString)