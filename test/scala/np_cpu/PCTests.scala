package np_cpu

import chisel3.iotesters._

class PCTests (pc : PC) extends PeekPokeTester(pc) {
  for(i <- 0 until 10){
    expect(pc.io.pc_ptr, (4*i))
	println("Hello!!!!")
	step(1)
  }
}

object PCTests extends App {
  Driver(() => new PC()) {pc =>
    new PCTests(pc)
  }
}