package Bye

import chisel3._
import chisel3.util._

class Plink extends Bundle{
  val data   = UInt(16.W)
  val valid  = Bool()
  val parity = UInt(5.W)
}

class FilterIO [T <: Data] (t : T) extends Bundle{
  val x = Flipped(Input(t))
  val y = Output(t)
}

class Filter [T <: Data] (t : T) extends Module{
  val io = IO(new FilterIO(t))
}

/*
class Block (t : Plink )extends Module {
  val block = Module(new Filter(new Plink()))
}
*/

object Filter extends App{
  (new chisel3.stage.ChiselStage).emitVerilog(new Filter(new Plink()))
}