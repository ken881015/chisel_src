package aias_lab2

import chisel3._
import chisel3.util._

class TrafficLight(Ytime:Int, Gtime:Int, Ptime:Int) extends Module {
  val io = IO(new Bundle{
    val H_traffic = Output(UInt(2.W))
    val V_traffic = Output(UInt(2.W))
    val P_traffic = Output(UInt(1.W))
    val timer     = Output(UInt(5.W)) 
  })
  
  val Off = 0
  val Red = 1
  val Yellow = 2
  val Green = 3
  
  val sIdle :: sHGVR :: sHYVR :: sHRVG :: sHRVY :: sPG :: Nil = Enum(6)

  val state = RegInit(sIdle)

  when(state === sIdle){
    state := sHGVR
  }.elsewhen(state === sHGVR){
    state := Mux(cntReg===Gtime.U,sHYVR,sHGVR)
  }.elsewhen(state === sHYVR){
    state := Mux(cntReg===Ytime.U,sHRVG,sHYVR)
  }.elsewhen(state === sHRVG){
    state := Mux(cntReg===Gtime.U,sHRVY,sHRVG)
  }.elsewhen(state === sHRVY){
    state := Mux(cntReg===Ytime.U,sPG,sHRVY)
  }.elsewhen(state === sPG){
    state := Mux(cntReg===Ptime.U,sHGVR,sPG)
  }.otherwise{
    state := sIdle
  }
  
  val Tlimit = Wire(UInt(4.W))
  val cntReg = RegInit(0.U(4.W))
  cntReg := Mux(cntReg===Tlimit, 0.U, cntReg+1.U)
  
  //Default statement
  io.H_traffic := Off.U
  io.V_traffic := Off.U
  io.P_traffic := Off.U

  switch(state){
    is(sHGVR){
      io.H_traffic := Green.U
      io.V_traffic := Red.U
      io.P_traffic := Red.U
      Tlimit := Gtime.U
    }
    is(sHYVR){
      io.H_traffic := Yellow.U
      io.V_traffic := Red.U
      io.P_traffic := Red.U
      Tlimit := Ytime.U
    }
    is(sHRVG){
      io.H_traffic := Red.U
      io.V_traffic := Green.U
      io.P_traffic := Red.U
      Tlimit := Gtime.U
    }
    is(sHRVY){
      io.H_traffic := Red.U
      io.V_traffic := Yellow.U
      io.P_traffic := Red.U
      Tlimit := Ytime.U
    }
    is(sPG){
      io.H_traffic := Red.U
      io.V_traffic := Red.U
      io.P_traffic := Green.U
      Tlimit := Ptime.U
    }
  }

  io.timer := 0.U
}

object TLDriver extends App{
  val Ytime = 3
  val Gtime = 7
  val Ptime = 5
  Driver.execute(args,() =>new TrafficLight(Ytime,Gtime,Ptime))
}