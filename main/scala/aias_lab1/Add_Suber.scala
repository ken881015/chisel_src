package aias_lab1

import chisel3._
import chisel3.util._

class Add_Suber extends Module{
  val io = IO(new Bundle{
    val in_1 = Input(UInt(4.W))
	val in_2 = Input(UInt(4.W))
	val op = Input(Bool()) // 0:ADD 1:SUB
	val out = Output(SInt(4.W))
	val o_f = Output(Bool())
  })
  
  val in_2_alt = Wire(UInt(4.W))
  in_2_alt := io.in_2 ^ Fill(4,io.op)
  
  
  val a0 = Module(new FullAdder())
  a0.io.A := io.in_1(0)
  a0.io.B := in_2_alt(0)
  a0.io.Cin := io.op
  
  val a1 = Module(new FullAdder())
  a1.io.A := io.in_1(1)
  a1.io.B := in_2_alt(1)
  a1.io.Cin := a0.io.Cout
  
  val a2 = Module(new FullAdder())
  a2.io.A := io.in_1(2)
  a2.io.B := in_2_alt(2)
  a2.io.Cin := a1.io.Cout
  
  
  val a3 = Module(new FullAdder())
  a3.io.A := io.in_1(3)
  a3.io.B := in_2_alt(3)
  a3.io.Cin := a2.io.Cout
  
  
  io.out := Cat(a3.io.S,
				a2.io.S,
				a1.io.S,
				a0.io.S).asSInt
				
  io.o_f := a2.io.Cout ^ a3.io.Cout
}

/*
object ASDriver extends App{
  Driver.execute(args,() => new Add_Suber)
}
*/