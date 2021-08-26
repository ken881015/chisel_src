package np_cpu

import chisel3._
import chisel3.util._

class DataMemIO extends Bundle {
  val wen   = Input(UInt(1.W))
  val waddr = Input(UInt(32.W))
  val wdata = Input(UInt(32.W))
  val ren   = Input(UInt(1.W))
  val raddr = Input(UInt(32.W))
  val rdata = Output(UInt(32.W))
}

class DataMem extends Module{
  val io = IO(new DataMemIO())
  
  val mem = Mem(256, UInt(8.W)) //32 32-bits Reg as Mem Array
  
  io.rdata := 0.U

  
  when(io.wen.asBool){
    Cat(mem(io.waddr),mem(io.waddr + 1.U),mem(io.waddr + 2.U),mem(io.waddr + 3.U)) := io.wdata
  }
  when(io.ren.asBool){
    io.rdata := Cat(mem(io.raddr),mem(io.raddr + 1.U),mem(io.raddr + 2.U),mem(io.raddr + 3.U))
  }
  
}

object DataMem extends App{
  (new chisel3.stage.ChiselStage).emitVerilog(new DataMem())
}