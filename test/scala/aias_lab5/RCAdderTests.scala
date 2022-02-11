package aias_lab5

import chisel3.iotesters.{Driver,PeekPokeTester}

class RCAdderTests (dut:RCAdder) extends PeekPokeTester(dut){

    val in1 = Array(5,32,1,77,34,55,12)
    val in2 = Array(3456,89489,78,5216,4744,8,321)
    
    //in1.zip(in2)
    (in1 zip in2).foreach{
      case(i,j)=>
          println("i = "+i.toString)
          println("j = "+j.toString)
          poke(dut.io.In1,i)
          poke(dut.io.In2,j)
          expect(dut.io.Sum,i+j)
    }


    // for(i <- in1){
    //     for(j <- in2){
    //         poke(dut.io.In1,i)
    //         poke(dut.io.In2,j)
    //         expect(dut.io.Sum,i+j)
    //     }
    // }

    println("RCAdder test completed!!!!!")
}

object RCAdderTests extends App{
    Driver.execute(args,()=>new RCAdder(32)){
        c => new RCAdderTests(c)
    }
}