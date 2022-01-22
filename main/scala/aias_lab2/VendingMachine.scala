package aias_lab2

import chisel3._
import chisel3.util._

class VendingMachine(Prices:Seq[Int]) extends Module{
  
  val pricesReg = RegInit(VecInit(Prices.map(x=>x.U(8.W))))

  val io =  IO(new Bundle{
    val Drinks_button = Input(Vec(Prices.length,Bool()))
    val Coin = Input(UInt(8.W))
    val Drinks_light = Output(Vec(Prices.length,Bool()))
    val out = Output(Bool())
  })

  val Balance = RegInit(0.U(8.W))
  Balance := Balance + io.Coin

//   def pwm(nrCycles:Int , din:UInt) = {
//       val cntReg = RegInit(0.U(unsignedBitLength(nrCycles-1).W))
//       cntReg := Mux(cntReg === (nrCycles-1).U , 0.U , cntReg + 1.U)
//       din > cntReg
//   }

//   val din = 3.U
  io.out := false.B
}

// object VMDriver extends App{
//   val Prices = Seq(30,40,50)
//   Driver.execute(args,()=>new VendingMachine(Prices))
// }