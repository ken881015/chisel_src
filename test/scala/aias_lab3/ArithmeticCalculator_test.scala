package aias_lab3

import chisel3.iotesters.{Driver,PeekPokeTester}

class ArithmeticCalculatorTests (dut : ArithmeticCalculator) extends PeekPokeTester(dut){
  val Dict = Map(('0',0),('1',1),('2',2),('3',3),('4',4),('5',5),('6',6),('7',7),('8',8),('9',9),('+',10),('-',11),('*',12),('(',13),(')',14),('=',15))
  val formular = "56*32+13="

  for(i <- 0 until formular.length){
    poke(dut.io.key_in,Dict(formular(i)))
    step(10)
  }
}
object ACTester extends App{
  Driver.execute(args,() => new ArithmeticCalculator()){
    c => new  ArithmeticCalculatorTests(c)
  }
}