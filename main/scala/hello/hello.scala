package hello

import chisel3._
import chisel3.iotesters.{PeekPokeTester, Driver}

class Hello extends Module {
  val io = IO(new Bundle { 
    val out = Output(UInt(8.W))
  }) 
  io.out := 42.U 
}

object HelloDriver extends App {
  chisel3.Driver.execute(args, () => new Hello)
}