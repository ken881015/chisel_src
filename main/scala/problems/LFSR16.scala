// See LICENSE.txt for license details.
package problems

import chisel3._
import chisel3.util._ //import when you need to use "Cat",etc

// Problem:
//
// Implement a 16-bit Fibonacci Linear-feedback shift register
// with polynomial x^16 + x^14 + x^13 + x^11 + 1
// State change is allowed only when 'inc' is asserted
//
class LFSR16 extends Module {
  val io = IO(new Bundle {
    val inc = Input(Bool())
    val out = Output(UInt(16.W))
  })

  // Implement below ----------

  val reg = RegInit(1.U(16.W))
  
  when(io.inc){
    val next_reg = Cat(reg(0)^reg(2)^reg(3)^reg(5),reg(15,1))
	reg := next_reg
  }
  io.out := reg

  // Implement above ----------
}