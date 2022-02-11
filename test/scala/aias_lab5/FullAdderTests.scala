package aias_lab5

import chisel3.iotesters.{PeekPokeTester,Driver}

class FullAdderTests (fa : FullAdder) extends PeekPokeTester(fa){
  for(a <- 0 until 2){
    for(b <- 0 until 2){
	  for(c <- 0 until 2){
	    poke(fa.io.A,a)
		poke(fa.io.B,b)
		poke(fa.io.Cin,c)
		
		var x = c & (a^b)
		var y = a & b
		
		expect(fa.io.S,(a^b^c))
		expect(fa.io.Cout,(x|y))
		step(1)
	  }
	}
  }
  println("FullAdder test completed!!!")
}

//>>>test:runMain aias_lab1.FAtester -td generated/ -tbn verilator
object FullAdderTests extends App{
  Driver.execute(args,() => new FullAdder()){
	c => new FullAdderTests(c)
  }
}
