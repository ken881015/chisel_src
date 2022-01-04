package aias_lab2

import chisel3.iotesters.{Driver,PeekPokeTester}

class VendingMachineTests (dut:VendingMachine) extends PeekPokeTester(dut){
  step(50)
}

// object VMTester extends App{
//   Driver.execute(args,() => new VendingMachine()){
//       c => new VendingMachineTests(c)
//   }
// } 

// object FAtester extends App{
//   Driver.execute(args,() => new FullAdder()){
// 	c => new FullAdderTests(c)
//   }
// }