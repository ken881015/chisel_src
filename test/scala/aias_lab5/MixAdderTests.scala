package aias_lab5

import chisel3.iotesters.{Driver,PeekPokeTester}

class MixAdderTests (dut:MixAdder) extends PeekPokeTester(dut){

    // val in1 = Array(5,32,1,77,34,55,12)
    // val in2 = Array(3456,89489,78,5216,4744,8,321)
    
    //in1.zip(in2)
    // (in1 zip in2).foreach{
    //   case(i,j)=>
    //       println("i = "+i.toString)
    //       println("j = "+j.toString)
    //       poke(dut.io.In1,i)
    //       poke(dut.io.In2,j)
    //       expect(dut.io.Sum,i+j)
    // }

    for (i <- 0 until 10){
        val x = rnd.nextInt(1<<4)
        val y = rnd.nextInt(1<<4)
        println("x = "+x.toString)
        println("y = "+y.toString)

        poke(dut.io.in1,x)
        poke(dut.io.in2,y)
        expect(dut.io.Sum,x+y)
    }

    println("MixAdder test completed!!!!!")
}

object MixAdderTests extends App{
    Driver.execute(args,()=>new MixAdder(8)){
        c => new MixAdderTests(c)
    }
}