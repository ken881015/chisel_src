package aias_lab1

import chisel3.iotesters.{PeekPokeTester,Driver}

class Add_SuberTests (as : Add_Suber) extends PeekPokeTester(as){
  var total = 0
  var num_correct = 0
  for(in1 <- -8 until 8){
    for(in2 <- -8 until 8){
	  for(op <- 0 to 1){
	    poke(as.io.in_1,in1)
		poke(as.io.in_2,in2)
		poke(as.io.op,op)
		val answer = if(op==0){in1+in2}else{in1-in2} 
		
		if(answer < -8 || answer > 7){
          expect(as.io.o_f,1)
		}else{
          expect(as.io.out,answer)
		}
	  }
	}
  }
}

//>>>test:runMain aias_lab1.FAtester -td generated/ -tbn verilator
object AStester extends App{
  Driver.execute(args,() => new Add_Suber()){
	c => new Add_SuberTests(c)
  }
}
