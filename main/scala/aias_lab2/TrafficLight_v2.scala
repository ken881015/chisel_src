package aias_lab2

import chisel3._
import chisel3.util._

class TrafficLight_v2(Ytime:Int, Gtime:Int, Ptime:Int) extends Module{
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
  val state = RegInit(sIdle)
  val last_state = RegInit(sHGVR)
  
  val cntLoad = WireDefault(false.B)
  val cntMode = WireDefault(0.U(2.W))
  val cntReg = RegInit(0.U(4.W))
  val cntDone = Wire(Bool())
  cntDone := cntReg === 0.U
  
  when(!cntDone){
    cntReg := cntReg - 1.U
  }
  when(cntLoad){

    when(cntMode === 0.U){
      cntReg := (Gtime-1).U
    }.elsewhen(cntMode === 1.U){
      cntReg := (Ytime-1).U
    }.elsewhen(cntMode === 2.U){
      cntReg := (Ptime-1).U
    }

    when(io.P_button && state=/=sPG){
      cntReg := (Ptime-1).U
    }
  }

  io.timer := cntReg
  
  cntLoad := cntDone || (io.P_button && state=/=sPG)
  
  //Next_State FSM
  switch(state){
    is(sIdle){
      cntLoad := true.B
      cntMode := 0.U
      state := sHGVR
    }
    is(sHGVR){
      cntMode := 1.U
      when(io.P_button){
        last_state := sHGVR
        state := sPG
      }.elsewhen(cntDone){
        state := sHYVR
      }
    }
    is(sHYVR){
      cntMode := 0.U
      when(io.P_button){
        last_state := sHYVR
        state := sPG
      }.elsewhen(cntDone){
        state := sHRVG
      }
    }
    is(sHRVG){
      cntMode := 1.U
      when(io.P_button){
        last_state := sHRVG
        state := sPG
      }.elsewhen(cntDone){
        state := sHRVY
      }
    }
    is(sHRVY){
      cntMode := 2.U
      when(io.P_button){
        last_state := sHRVY
        state := sPG
      }.elsewhen(cntDone){
        state := sPG
      }
    }
    is(sPG){
      cntMode := 0.U
      when(last_state === sHGVR || last_state === sHRVG){
        cntMode := 0.U
      }.elsewhen(last_state === sHYVR || last_state === sHRVY){
        cntMode := 1.U
      }
      when(cntDone){
        state := last_state
        last_state := sHGVR
      }
    }
  }
  
  //Comvination Output
  //Default statement
  io.H_traffic := Off
  io.V_traffic := Off
  io.P_traffic := Off
   
  switch(state){
    is(sHGVR){
      io.H_traffic := Green
      io.V_traffic := Red
      io.P_traffic := Red
    }
    is(sHYVR){
      io.H_traffic := Yellow
      io.V_traffic := Red
      io.P_traffic := Red
    }
    is(sHRVG){
      io.H_traffic := Red
      io.V_traffic := Green
      io.P_traffic := Red
    }
    is(sHRVY){
      io.H_traffic := Red
      io.V_traffic := Yellow
      io.P_traffic := Red
    }
    is(sPG){
      io.H_traffic := Red
      io.V_traffic := Red
      io.P_traffic := Green
    }
  }
}

// object TLv2Driver extends App{
//   Driver.execute(args, () => new TrafficLight_v2(2,4,3))
// }