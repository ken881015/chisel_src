package np_cpu

import chisel3.iotesters._
import ALU_ctrl._

class ALUTests (alu : ALU) extends PeekPokeTester(alu) {
  //45 + 36 = 81
  poke(alu.io.src1,45)
  poke(alu.io.src2,36)
  poke(alu.io.shamt,3)
  poke(alu.io.ALUCtrl,ctrl_ADD)
  expect(alu.io.ALUout,81)
  step(1)
  //12 & 5 = 4
  poke(alu.io.src1,12)
  poke(alu.io.src2,5)
  poke(alu.io.shamt,3)
  poke(alu.io.ALUCtrl,ctrl_AND)
  expect(alu.io.ALUout,4)
  step(1)
  //57 - 36 = 21
  poke(alu.io.src1,57)
  poke(alu.io.src2,36)
  poke(alu.io.shamt,3)
  poke(alu.io.ALUCtrl,ctrl_SUB)
  expect(alu.io.ALUout,21)
  step(1)
  
  //12 | 7 = 15
  poke(alu.io.src1,12)
  poke(alu.io.src2,7)
  poke(alu.io.shamt,3)
  poke(alu.io.ALUCtrl,ctrl_OR)
  expect(alu.io.ALUout,15)
  step(1)
  
  //12 ^ 7 = 11
  poke(alu.io.src1,12)
  poke(alu.io.src2,7)
  poke(alu.io.shamt,3)
  poke(alu.io.ALUCtrl,ctrl_OR)
  expect(alu.io.ALUout,11)
  step(1)
}

/*
object ALUTests extends App {
  Driver(() => new ALU()) {alu =>
    new ALUTests(alu)
  }
}
*/

//REFERENCE : https://zhuanlan.zhihu.com/p/161966317

object ALUTests extends App {
  chisel3.iotesters.Driver.execute(args, () => new ALU())(c => new ALUTests(c))
}
