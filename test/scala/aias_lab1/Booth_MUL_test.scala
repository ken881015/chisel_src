package aias_lab1

import chisel3.iotesters.{Driver,PeekPokeTester}

class Booth_MUL_test(dut:Booth_MUL) extends PeekPokeTester(dut) {
  for(a <- -127 to 127 if a/16 == 0){
      for(b <- -127 to 127 if b/16 == 0){
          poke(dut.io.in1,a)
          poke(dut.io.in2,b)
          if(peek(dut.io.out).toShort != a*b)
            println("A: "+a+" B: "+b+" get "+peek(dut.io.out).toShort)
          step(1)
      }
  }
}

object BMTester extends App{
    Driver.execute(args,() => new Booth_MUL(8)){
        c => new Booth_MUL_test(c)
    }
}
