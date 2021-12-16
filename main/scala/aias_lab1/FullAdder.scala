package aias_lab1

import chisel3._

class FullAdder extends Module{
    val io = IO(new Bundle{
        val A = Input(UInt(1.W))
        val B = Input(UInt(1.W))
        val Cin = Input(UInt(1.W))
        
        val S = Output(UInt(1.W))
        val Cout = Output(UInt(1.W))
    })
    
    val w1 = Wire(UInt(1.W))


    w1 := io.A ^ io.B
    io.S := w1 ^ io.Cin
    io.Cout := (io.Cin&w1)|(io.A&io.B)
}

//Verilog and FIRRTL file generator
//>>>runMain aias_lab1.FADriver -td generated/FullAdder
object FADriver extends App {
    chisel3.Driver.execute(args, () => new FullAdder)
}

