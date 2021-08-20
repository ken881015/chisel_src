// See LICENSE.txt for license details.
package problems

import chisel3._
import chisel3.util.{Valid, DeqIO}
import chisel3.stage.ChiselStage

// Problem:
// Implement a GCD circuit (the greatest common divisor of two numbers).
// Input numbers are bundled as 'RealGCDInput' and communicated using the Decoupled handshake protocol
//
class RealGCDInput extends Bundle {
  val a = UInt(16.W)
  val b = UInt(16.W)
}

class RealGCD extends Module {
  val io  = IO(new Bundle {
    val in  = DeqIO(new RealGCDInput())
    val out = Output(Valid(UInt(16.W)))
  })

  // Implement below ----------
  val x = Reg(UInt())
  val y = Reg(UInt())
  val p = RegInit(false.B)
  
  when(io.in.valid && !p){
    x := io.in.bits.a
	y := io.in.bits.b
	p := true.B
  }
  
  io.in.ready := !p
  
  when (p) {
    when (x >= y) {
	  x := x-y
	  y := y
	}.otherwise{
      x := y 
      y := x	
    }
  }
  io.out.bits := y
  io.out.valid := (x === 0.U) && p
  when(io.out.valid){
    p := false.B
  }
  
  
  // Implement above ----------
}

//(new ChiselStage).emitVerilog(new RealGCD)
