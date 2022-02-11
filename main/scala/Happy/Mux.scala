package Happy  //declare Module所屬的package，最好和存放的資料夾同名

import chisel3._
import chisel3.stage.ChiselStage

//撰寫Module，記得要extends Moudle
class Mux2 extends Module {
  //IO port的宣告，格式: val io = IO(new Bundle{...})
  val io = IO(new Bundle {
    //在Bundle內宣告IO port，格式: val 變數名稱 = Input/Output(dtype(長度))
    val sel = Input(UInt(1.W))
    val in0 = Input(UInt(1.W))
    val in1 = Input(UInt(1.W))
    val out = Output(UInt(1.W))
  })

  def my_Mux(sel:Bool,in1:UInt,in0:UInt) : UInt = {
    val x = Wire(UInt())
    when(sel){x := in1}
    .otherwise{x := in0}
    x
  }

  //電路的行為描述
  io.out := my_Mux(io.sel.asBool,io.in1,io.in0)
}
//=================================================================

//寫完Module後，必須建立一個main函數作為scala的入口，有多種寫法。

//方法一:chisel3.Driver。
object Mux2 extends App{
  chisel3.Driver.execute(args,()=>new Mux2)
}

//方法二:chisel3.stage.ChiselStage
// object Mux2 extends App {
//   (new chisel3.stage.ChiselStage).emitVerilog(new Mux2, args)
// }