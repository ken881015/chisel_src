// See LICENSE.txt for license details.
package made

import chisel3.iotesters.PeekPokeTester
import chisel3.iotesters.Driver

class made_TrafficLightTests(c: made_TrafficLight) extends PeekPokeTester(c) {
  for(i <- 0 until 30){
    //println(s"TIME : ${c.io.timer.peek()}")
    step(1)
    //println("---------------------------------")
  }
  println("SUCCESS!!")
}

object made_TrafficLightTests extends App{
  Driver(() => new made_TrafficLight()){Tlignt =>
      new made_TrafficLightTests(Tlignt)
  }
}