package aias_lab1

import chisel3.iotesters.{Driver,PeekPokeTester}

class Booth_MUL_test(dut:Booth_MUL) extends PeekPokeTester(dut) {
  for(a <- -127 to 127){
      for(b <- -127 to 127){
          poke(dut.io.in1,a)
          poke(dut.io.in2,b)
          if(peek(dut.io.out).toShort != a*b)
            println("A: "+a+" B: "+b+" get "+peek(dut.io.out))
          step(1)
      }
  }
}

object BMTester extends App{
    Driver.execute(args,() => new Booth_MUL(8)){
        c => new Booth_MUL_test(c)
    }
}
