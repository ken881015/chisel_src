package np_cpu

import chisel3.iotesters._

class ALUTests (alu : ALU) extends PeekPokeTester(alu) {
  poke(alu.io.src1,5)
  poke(alu.io.src2,10)
  expect(alu.io.out,5)
}


object ALUTests extends App {
  Driver(() => new ALU()) {alu =>
    new ALUTests(alu)
  }
}


//REFERENCE : https://zhuanlan.zhihu.com/p/161966317
/*
object ALUTests extends App {
  chisel3.iotesters.Driver.execute(args, () => new ALU())(c => new ALUTests(c))
}
*/