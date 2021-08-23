package Bye

import chisel3._
import chisel3.util._

class DataBundle extends Bundle{
  val A = UInt(32.W)
  val B = UInt(32.W)
}

/*
object FifoDemo {
  def apply () = new Fifo(new DataBundle , 32)
}
*/

class Fifo[T <: Data] (t:T , n:Int) extends Module{
  val io = IO(new Bundle{
    val D = Input(Bool())
	val E = Input(UInt(n.W))
	val F = Output(UInt(n.W))
	val G = Input (t)
	val H = Output(t)
  })
  
  io.F := 6.U
}

object Fifo extends App{
  (new chisel3.stage.ChiselStage).emitVerilog(new Fifo(new DataBundle(),16))
}