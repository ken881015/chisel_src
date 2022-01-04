package aias_lab2

import chisel3._
import chisel3.util._

class TrafficLight(Ytime:Int, Gtime:Int, Ptime:Int) extends Module {
  val io = IO(new Bundle{
    val P_button = Input(Bool())
    val H_traffic = Output(UInt(2.W))
    val V_traffic = Output(UInt(2.W))
    val P_traffic = Output(UInt(2.W))
    val timer     = Output(UInt(5.W)) 
  })
  
  val Off = 0.U
  val Red = 1.U
  val Yellow = 2.U
  val Green = 3.U
  
  val sIdle :: sHGVR :: sHYVR :: sHRVG :: sHRVY :: sPG :: Nil = Enum(6)
  
  val last_state = RegInit(sIdle)
  val state = RegInit(sIdle)

  val cntReg = RegInit(0.U(4.W))
  cntReg := Mux(
  ((cntReg===(Gtime-1).U)&&(state===sHGVR||state===sHRVG))||
  ((cntReg===(Ytime-1).U)&&(state===sHYVR||state===sHRVY))||
  ((cntReg===(Ptime-1).U)&&(state===sPG))||
  ((io.P_button===true.B)&&(state=/=sPG)), 
   0.U,
   cntReg+1.U)
  
  //Default statement
  state := sIdle
  io.H_traffic := Off
  io.V_traffic := Off
  io.P_traffic := Off

  switch(state){
    is(sIdle){
      io.H_traffic := Off
      io.V_traffic := Off
      io.P_traffic := Off

      state := sHGVR
    }
    is(sHGVR){
      io.H_traffic := Green
      io.V_traffic := Red
      io.P_traffic := Red

      when(io.P_button){
        state := sPG
        last_state := sHGVR
      }.elsewhen(cntReg===(Gtime-1).U){
        state := sHYVR
      }.otherwise{
        state := sHGVR
      }
    }
    is(sHYVR){
      io.H_traffic := Yellow
      io.V_traffic := Red
      io.P_traffic := Red

      when(io.P_button){
        state := sPG
        last_state := sHYVR
      }.elsewhen(cntReg===(Ytime-1).U){
        state := sHRVG
      }.otherwise{
        state := sHYVR
      }
    }
    is(sHRVG){
      io.H_traffic := Red
      io.V_traffic := Green
      io.P_traffic := Red

      when(io.P_button){
        state := sPG
        last_state := sHRVG
      }.elsewhen(cntReg===(Gtime-1).U){
        state := sHRVY
      }.otherwise{
        state := sHRVG
      }
    }
    is(sHRVY){
      io.H_traffic := Red
      io.V_traffic := Yellow
      io.P_traffic := Red

      when(io.P_button){
        state := sPG
        last_state := sHRVY
      }.elsewhen(cntReg===(Ytime-1).U){
        state := sPG
      }.otherwise{
        state := sHRVY
      }
    }
    is(sPG){
      io.H_traffic := Red
      io.V_traffic := Red
      io.P_traffic := Green
      state := Mux(cntReg===(Ptime-1).U,last_state,sPG)

      //reset
      last_state := Mux(cntReg===(Ptime-1).U,sHGVR,last_state)
    }
  }
  io.timer := cntReg
}

object TLDriver extends App{
  val Ytime = 3
  val Gtime = 7
  val Ptime = 5
  Driver.execute(args,() =>new TrafficLight(Ytime,Gtime,Ptime))
}