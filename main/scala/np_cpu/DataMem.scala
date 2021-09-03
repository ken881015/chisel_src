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
  
  val mem = Mem(256, UInt(8.W)) //32 * 32-bits Reg as Mem Array
  
  io.rdata := 0.U

  
  when(io.wen.asBool){
    printf("waddr = %d\n",io.waddr)
    printf("wdata = %d\n",io.wdata)
    mem(io.waddr)      := io.wdata(31,24)
	mem(io.waddr + 1.U):= io.wdata(23,16)
	mem(io.waddr + 2.U):= io.wdata(15,8)
	mem(io.waddr + 3.U):= io.wdata(7,0)
  }
  when(io.ren.asBool){
    io.rdata := Cat(mem(io.raddr),mem(io.raddr + 1.U),mem(io.raddr + 2.U),mem(io.raddr + 3.U))
  }
  
  for(i <- 0 until 5){
    printf("Mem[%d] is %d\n",(4.U * i.U),Cat(Cat(mem(4*i) ,mem((4*i) + 1),mem((4*i) + 2),mem((4*i) + 3))))
  }
  
}

object DataMem extends App{
  (new chisel3.stage.ChiselStage).emitVerilog(new DataMem())
}