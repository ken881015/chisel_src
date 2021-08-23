// See LICENSE.txt for license details.
package hello

import chisel3._
import chisel3.util._
import chisel3.iotesters.{PeekPokeTester, Driver}

class Hello extends Module {
  val io = IO(new Bundle {
    val out = Output(UInt(8.W))
  })
  io.out := 42.U
  /*
  val n = 10
  val Pi = math.Pi
  val times = Range(0,n,1).map(i => (i*2*Pi)/(n.toDouble - 1) - Pi)
  val inits = times.map(t => math.round(math.sin(t) * 8).asSInt(32.W))
  
  for(i <- 0 until 10){
    println("times("+ i +") = " + times(i))
  }
  for(i <- 0 until 10){
    println("inits("+ i +") = " + inits(i))
  }
  */
  
}

class HelloTests(c: Hello) extends PeekPokeTester(c) {
  step(1)
  expect(c.io.out, 42)
}

object Hello {
  def main(args: Array[String]): Unit = {
    if (!Driver(() => new Hello())(c => new HelloTests(c))) System.exit(1)
  }
}
