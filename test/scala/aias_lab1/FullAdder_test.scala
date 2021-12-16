package aias_lab1

import chisel3.iotesters.{PeekPokeTester,Driver}

class FullAdderTests (fa : FullAdder) extends PeekPokeTester(fa){
  poke(fa.io.A,0)
  poke(fa.io.B,1)
  poke(fa.io.Cin,0)
  
  expect(fa.io.S,1)
  expect(fa.io.Cout,0)
}

object FAtester extends App{
    chisel3.iotesters.Driver.execute(args,() => new FullAdder)(c => new FullAdderTests(c))
}