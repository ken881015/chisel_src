package test

import chisel3._
import chisel3.util._
import chisel3.iotesters._

class SRA extends Module{
  val io = IO(new Bundle{
    val A = Output(SInt(4.W))
	val B = Output(UInt(4.W))
  })
  
  val x = RegInit(8.U(4.W))
  
  //io.A := (x.asSInt >> 1).asUInt
  io.B := (("b1000".U).asSInt >> 2).asUInt
  io.A := -4.asSInt(4.W) >> 2
}

class SRATests (sra : SRA) extends PeekPokeTester(sra) {
  expect(sra.io.A,-4)
  println("HELLO!!!")
  println("sign = " + peek(sra.io.A).toString)
  println("unsign = " + peek(sra.io.B).toString)
}

/*
object SRA extends App{
  //chisel3.Driver.execute(args, () => new SRA())
  (new chisel3.stage.ChiselStage).emitVerilog(new SRA())
}
*/

object SRATests extends App {
  chisel3.iotesters.Driver.execute(args, () => new SRA())(c => new SRATests(c))
}