package np_cpu

import chisel3.iotesters._

class ALUTests (alu : ALU) extends PeekPokeTester(alu) {
  poke(alu.io.in1,5)
  poke(alu.io.in2,10)
  expect(alu.io.out,5)
}

object ALUTests extends App {
  Driver(() => new ALU()) {alu =>
    new ALUTests(alu)
  }
}