package aias_lab5

import chisel3._

class MixAdder (n:Int) extends Module{
  val io = IO(new Bundle{
      val Cin = Input(UInt(1.W))
      val in1 = Input(UInt((4*n).W))
      val in2 = Input(UInt((4*n).W))
      val Sum = Output(UInt((4*n).W))
      val Cout = Output(UInt(1.W))
  })

  // in1 in2 Cin Sum Cout
  val CLA_Array = Array.fill(n)(Module(new CLAdder).io)
  val C = Wire(Vec(n+1, UInt(1.W)))
  val S = Wire(Vec(n, UInt(4.W)))

  C(0) := io.Cin

  for (i <- 0 until n) {
    CLA_Array(i).in1 := io.in1(3+4*i,4*i)
    CLA_Array(i).in2 := io.in2(3+4*i,4*i)
    CLA_Array(i).Cin := C(i)
    C(i+1) := CLA_Array(i).Cout
    S(i) := CLA_Array(i).Sum
  }

  io.Sum := S.asUInt
  io.Cout := C(n)
}

object MixAdder extends App{
    Driver.execute(args,()=>new MixAdder(8))
}