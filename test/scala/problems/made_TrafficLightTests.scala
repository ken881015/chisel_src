// See LICENSE.txt for license details.
package problems

import chisel3.iotesters.PeekPokeTester

class made_TrafficLightTests(c: made_TrafficLight) extends PeekPokeTester(c) {
  for(i <- 0 until 30){
    //println(s"TIME : ${c.io.timer.peek()}")
    step(1)
    //println("---------------------------------")
  }
  println("SUCCESS!!")
}