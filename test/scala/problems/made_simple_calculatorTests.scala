// See LICENSE.txt for license details.
package problems

import chisel3.iotesters.PeekPokeTester

class made_simple_calculatorTests(c: made_simple_calculator) extends PeekPokeTester(c) {
    val input_0 = Seq(11,3,6,13)            // -36       =
    val input_1 = Seq(11,3,1,12,11,6,13)    // -31 * -6  = 
    val input_2 = Seq(3,4,0,10,11,3,0,13)   // 340 + -30 = 
    val input_3 = Seq(3,0,0,12,11,9,13)     // 300 * -9  =
    val input_4 = Seq(10,9,6,11,1,0,0,13)   // +96 - 100 =
    val input_5 = Seq(11,3,5,10,11,3,2,13)  // -35 + -32 =
    val input_6 = Seq(10,9,9,10,10,9,9,13)  // +99 + +99 =
    val input_set = Seq(input_0,input_1,input_2,input_3,input_4,input_5,input_6)
    //println(input_set(0))
    /*
    c.clock.step(1)
    c.io.key_in.poke(6.U)
    c.clock.step(1)
    println("------------------------------")
    c.clock.step(1)
    println("------------------------------")
    */
    
    val x = 4
    
    for(i <- 0 until 10){
       step(1) 
       if(i < input_set(x).length) poke(c.io.key_in,input_set(x)(i))
       else poke(c.io.key_in,input_set(x)(input_set(x).length - 1))
//        println("---------------i = "+i+"---------------")
    }
    
    
//     c.io.key_in.poke(13.U) 
//     c.io.outcome.peek()
    println("SUCCESS")
}


//println(getVerilog(new simple_calculator))