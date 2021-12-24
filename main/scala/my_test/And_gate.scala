package my_test

import chisel3._
import chisel3.util._

class And_gate extends Module{
  val io = IO(new Bundle{
    val a = Input(Bool())
    val b = Input(Bool())

    val o = Output(Bool())
  })

  io.o := io.a & io.b
}

object And_gate extends App{
  Driver.execute(args,() => new And_gate)
}