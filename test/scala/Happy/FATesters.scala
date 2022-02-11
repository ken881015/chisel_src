package Happy

import chisel3.iotesters.{PeekPokeTester,Driver}

class FATesters (fa : Full_Adder) extends PeekPokeTester(fa){
  for(a <- 0 until 2){
    for(b <- 0 until 2){
      for(c <- 0 until 2){
        poke(fa.io.A,a)
        poke(fa.io.B,b)
        poke(fa.io.Cin,c)

        var x = c & (a^b)
        var y = a & b

        expect(fa.io.Sum,(a^b^c))
        expect(fa.io.Cout,(x&y))

        step(1)
      }
    }
  }
}

//>>>test:runMain aias_lab1.FAtester -td generated/ -tbn verilator
object FATesters extends App{
  Driver.execute(Array("-td","./generated"),() => new Full_Adder()){
    c => new FATesters(c)
  }
}