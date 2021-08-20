package mypack

import chisel3.iotesters._
import chisel3._



class AbcTests(c: Abc) extends PeekPokeTester(c) {
  poke(c.io.a , 40.U)
  poke(c.io.b , 32.U)
  expect(c.io.out, 72.U)
  println("RESULT = " + peek(c.io.out).toString)
}

object AbcTests extends App {
  chisel3.iotesters.Driver(() => new Abc()) { c =>
    new AbcTests(c)
  }
}