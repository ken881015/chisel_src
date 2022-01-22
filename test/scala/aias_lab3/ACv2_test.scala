package aias_lab3

import chisel3.iotesters.{Driver,PeekPokeTester}

class ACTests (dut : ArithmeticCalculator_v2,f:String) extends PeekPokeTester(dut){
  
  val Dict = Map(('0',0),('1',1),('2',2),('3',3),('4',4),('5',5),('6',6),('7',7),('8',8),('9',9),('+',10),('-',11),('*',12),('(',13),(')',14),('=',15))
  
  while(peek(dut.io.ready)==0)
    step(1)

  for(i <- 0 until f.length){
    poke(dut.io.key_in,Dict(f(i)))
    step(1)
  }

  while(peek(dut.io.valid)==0){
    println("Wait....")
    step(1)
  }
    
  println("\n\nThe answer of " + f + " " + peek(dut.io.Answer).toInt + "\n\n")
  
  }
object ACv2Tester extends App{
  Driver.execute(args,() => new ArithmeticCalculator_v2()){
    a => new  ACTests(a,args(0))
  }
}