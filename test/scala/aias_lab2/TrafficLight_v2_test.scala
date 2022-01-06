package aias_lab2

import scala.util.Random
import chisel3.iotesters.{Driver,PeekPokeTester}

class TrafficLightv2Tests (tl : TrafficLight_v2) extends PeekPokeTester(tl){

  step(23)
  
  for(i <- 0 until 10){
    poke(tl.io.P_button,true)
    step(1)
    poke(tl.io.P_button,false)

    step(Random.nextInt(20)+10)
  }
}

object TLv2Tester extends App{
  val Ytime = 3
  val Gtime = 7
  val Ptime = 5
  Driver.execute(args,() => new TrafficLight_v2(Ytime,Gtime,Ptime)){
    c => new TrafficLightv2Tests(c)
  }
}