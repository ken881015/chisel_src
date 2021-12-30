package aias_lab2

import chisel3._
import chisel3.util._

class VendingMachine extends Module{
  val io =  IO(new Bundle{
      val out = Output(Bool())
  })

  def pwm(nrCycles:Int , din:UInt) = {
      val cntReg = RegInit(0.U(unsignedBitLength(nrCycles-1).W))
      cntReg := Mux(cntReg === (nrCycles-1).U , 0.U , cntReg + 1.U)
      din > cntReg
  }

  val din = 3.U
  io.out := pwm(10,din)
}

object VMDriver extends App{
    Driver.execute(args,()=>new VendingMachine)
}