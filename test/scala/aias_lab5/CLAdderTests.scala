package aias_lab5

import chisel3.iotesters.{Driver,PeekPokeTester}

class CLAdderTests (dut:CLAdder) extends PeekPokeTester(dut){
    for(i <- 0 to  15){
        for(j <- 0 to 15){
            poke(dut.io.in1,i)
            poke(dut.io.in2,j)
            if(peek(dut.io.Cout)*16+peek(dut.io.Sum)!=(i+j)){
                println("Shit!!!!")
            }
        }
    }
    println("CLAdder test completed!!!")
}

object CLAdderTests extends App{
    Driver.execute(args,()=>new CLAdder){
        c => new CLAdderTests(c)
    }
}