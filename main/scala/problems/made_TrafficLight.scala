// See LICENSE.txt for license details.
package problems

import chisel3._
import chisel3.util._

class made_TrafficLight (val G_time : UInt = 3.U , val Y_time : UInt = 2.U) extends Module {
    val io = IO(new Bundle{
        val Horizontal_Light = Output(UInt(2.W))
        val Vertical_Light   = Output(UInt(2.W))
        val timer            = Output(UInt(5.W))
    })
    
    val sIdle :: sHRVG :: sHRVY :: sHGVR :: sHYVR :: Nil = Enum(5)
    
    val state      = RegInit(0.U(3.W)) //Sequential
    val next_state = Wire(UInt(3.W))   //Combinational
    
    val timer      = RegInit(0.U(10.W))
    val cnt        = RegInit(0.U(3.W))
    val limit = Mux(state === sHRVG || state === sHGVR , G_time , 
                Mux(state === sHRVY || state === sHYVR , Y_time , 0.U))
    
    when(state === sIdle){
        cnt := 0.U
    }.otherwise{
        cnt := Mux(cnt === limit - 1.U , 0.U , cnt + 1.U)
    }
    
    timer := timer + 1.U
    io.timer:= timer
    
    //Debuging Block
    /*
    printf("state = %d\n", state)
    printf("next_state = %d\n", next_state)
    printf("limit = %d\n", limit)
    */
    printf("TIME = %d  ", io.timer)
    
    when(io.Horizontal_Light === 1.U){
        printf("Horizontal : Red     ")
    }.elsewhen(io.Horizontal_Light === 2.U){
        printf("Horizontal : Yellow  ")
    }.elsewhen(io.Horizontal_Light === 3.U){
        printf("Horizontal : Green   ")
    }
    
    when(io.Vertical_Light === 1.U){
        printf("Vertical : Red\n")
    }.elsewhen(io.Vertical_Light === 2.U){
        printf("Vertical : Yellow\n")
    }.elsewhen(io.Vertical_Light === 3.U){
        printf("Vertical : Green\n")
    }
    
    state := next_state
    
    when (state === sIdle){
        next_state := sHRVG
    }.elsewhen(state === sHRVG ){
        when(cnt === limit - 1.U){
            next_state := sHRVY 
        }.otherwise{
            next_state := sHRVG 
        } 
    }.elsewhen(state === sHRVY){
        when(cnt === limit - 1.U){
            next_state := sHGVR 
        }.otherwise{
            next_state := sHRVY 
        } 
    }.elsewhen(state === sHGVR){
        when(cnt === limit - 1.U){
            next_state := sHYVR 
        }.otherwise{
            next_state := sHGVR
        } 
    }.elsewhen(state === sHYVR){
        when(cnt === limit - 1.U){
            next_state := sHRVG 
        }.otherwise{
            next_state := sHYVR
        } 
    }.otherwise{
        next_state := sIdle
    }
    
    //io.timer := cnt
    //red = 1.U yellow 2.U green = 3.U
    when (state === sHRVG){
        io.Horizontal_Light := 1.U
        io.Vertical_Light   := 3.U
    }.elsewhen(state === sHRVY){
        io.Horizontal_Light := 1.U
        io.Vertical_Light   := 2.U
    }.elsewhen(state === sHGVR){
        io.Horizontal_Light := 3.U
        io.Vertical_Light   := 1.U
    }.elsewhen(state === sHYVR){
        io.Horizontal_Light := 2.U
        io.Vertical_Light   := 1.U
    }.otherwise{
        io.Horizontal_Light := 0.U
        io.Vertical_Light   := 0.U
    }   
}