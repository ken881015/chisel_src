package Happy //盡量和Module同package，可以省去程式碼編寫路徑的麻煩。
import chisel3.iotesters.{PeekPokeTester,Driver}

class Mux2Tests(c: Mux2) extends PeekPokeTester(c) {
  val i0 = 0
  val i1 = 1

  for (s <- 0 until 2) {
    //格式：poke(port,value)
    //注意到，雖然在Module內部，port的dtype是chisel的dtype(UInt、SInt、Bool...)
    //但在tester這裡，value用scala有的Int dtype(32 bits)即可
    poke(c.io.sel, s)
    poke(c.io.in1, i1)
    poke(c.io.in0, i0)
    step(1) //對Comb Circuit而言，可有可無
    
    //第一種測試方法
    expect(c.io.out, if (s == 1) i1 else i0)
    
    // //第二種測試方法
    s match {
        case 0 => if (peek(c.io.out)!=i0) println("Error!!!!") else println("pass!!")
        case 1 => if (peek(c.io.out)!=i1) println("Error!!!!") else println("pass!!")
    }
  }
}

//一樣是提供入口(main)讓編譯器知道從這裡開始
object Mux2Tests extends App{
  Driver.execute(args,()=>new Mux2){
    c => new Mux2Tests(c)
  }
}