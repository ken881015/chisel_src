package aias_lab2

import scala.util.Random
import chisel3.iotesters.{Driver,PeekPokeTester}

class TrafficLightTests (tl : TrafficLight) extends PeekPokeTester(tl){

  step(Random.nextInt(20))
  
  for(i <- 0 until 5){
    poke(tl.io.P_button,true)
    step(1)
    poke(tl.io.P_button,false)

    step(Random.nextInt(40))
  }
}

object TLTester extends App{
  val Ytime = 3
  val Gtime = 7
  val Ptime = 5
  Driver.execute(args,() => new TrafficLight(Ytime,Gtime,Ptime)){
    c => new TrafficLightTests(c)
  }
}