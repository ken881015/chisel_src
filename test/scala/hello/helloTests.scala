package hello

import chisel3._
import chisel3.iotesters.{PeekPokeTester, Driver, ChiselFlatSpec}

class HelloTests(c: Hello) extends PeekPokeTester(c) {  
        step(1)  
        expect(c.io.out, 43)
}

object HelloTester extends App {
  chisel3.iotesters.Driver.execute(args, () => new Hello)(c => new HelloTests(c))
}