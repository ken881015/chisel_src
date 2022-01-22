package aias_lab1

import chisel3._
import chisel3.util._
import scala.annotation.switch

//------------------Radix 4---------------------
class Booth_MUL(width:Int) extends Module {
  val io = IO(new Bundle{
    val in1 = Input(UInt(width.W))      //multiplicand
    val in2 = Input(UInt(width.W))      //multiplier
    val out = Output(UInt((2*width).W)) //product
  })

  val half_width = width/2

  val neg_in1 = Wire(UInt(width.W))
  neg_in1 := ~io.in1 + 1.U
  
  //operation
  val op = Wire(Vec(half_width,UInt(3.W)))

  op(0) := Cat(io.in2(1),io.in2(0),0.U)
  for(i <- 1 until half_width){
    op(i) := Cat(io.in2(2*i+1),io.in2(2*i),io.in2(2*i-1))
  }
  
  //partial product
  val pp = Wire(Vec(half_width,UInt((width+1).W)))

  for(i <- 0 until half_width){
    // switch(op(i)){
    //   is("b000".U){pp(i) := 0.U}
    //   is("b001".U){pp(i) := 0.U}
    //   is("b010".U){pp(i) := 0.U}
    //   is("b011".U){pp(i) := 0.U}
    //   is("b100".U){pp(i) := 0.U}
    //   is("b101".U){pp(i) := 0.U}
    //   is("b110".U){pp(i) := 0.U}
    //   is("b111".U){pp(i) := 0.U}
    // }
    when(op(i)==="b000".U | op(i)==="b111".U){ //0
      pp(i) := 0.U
    }.elsewhen(op(i)==="b001".U | op(i)==="b010".U){ //1
      pp(i) := Cat(io.in1(width-1),io.in1)
    }.elsewhen(op(i)==="b101".U | op(i)==="b110".U){ //-1
      pp(i) := Cat(neg_in1(width-1),neg_in1)
    }.elsewhen(op(i)==="b100".U){ //-2
      pp(i) := neg_in1 << 1
    }.elsewhen(op(i)==="b011".U){
      pp(i) := io.in1 << 1
    }.otherwise{
      pp(i) := 0.U
    }
  }
  
  //Sign_extend , Shift
  val spp = Wire(Vec(half_width,UInt((2*width).W)))
  for(i <- 0 until half_width){
    spp(i) := Cat(Fill((width-1),pp(i)(width)),pp(i)) << (2*i)
  }

  io.out := spp.reduce(_+_)
}

// object BMDriver extends App{
//   //Driver.execute(args,() => new Booth_MUL(args(0).toInt))
//   Driver.execute(args,() => new Booth_MUL(8))
// }
