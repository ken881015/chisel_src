package my_test

import chisel3.iotesters.{PeekPokeTester,Driver}


class And_gate_test(dut: And_gate) extends PeekPokeTester(dut) {
  poke(dut.io.a,true)
  poke(dut.io.b,true)

  println("Result is :" + peek(dut.io.o).toString)

  step(5)
}

object And_gate_test extends App{
  Driver.execute(args,()=> new And_gate()){
    c => new And_gate_test(c)
  }
}
