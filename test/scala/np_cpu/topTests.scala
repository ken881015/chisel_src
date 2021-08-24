package np_cpu

import chisel3.iotesters._

class topTests (top : top) extends PeekPokeTester(top) {
  println(peek(top.io.out).toString)
  step(1)
  println(peek(top.io.out).toString)
  step(1)
  println(peek(top.io.out).toString)
  step(1)
  println(peek(top.io.out).toString)
  step(1)
  println(peek(top.io.out).toString)
}

object topTests extends App {
  Driver(() => new top()) {top =>
    new topTests(top)
  }
}