// See LICENSE.txt for license details.
package made

import chisel3._
import chisel3.util._

class made_simple_calculator extends Module{
    val io = IO(new Bundle{
        val key_in  = Input(UInt(5.W)) // 0~9 , 10:ADD , 11:SUB , 12:MUL 13:EQUAL 14:Delete , additional bit due to transformation to SInt
        val valid   = Output(Bool())
        val outcome = Output(SInt(20.W))  // Range : -524288 ~ 524287
    })
    val sIdle :: sIn1_sign :: sIn1_value :: sIn2_sign :: sIn2_value :: sOut :: sSleep :: Nil = Enum(7)
    val zero :: one :: two :: three :: four :: five :: six :: seven :: eight :: nine :: add :: sub :: mul :: eq :: del :: Nil = Enum(15);
    
    val state = RegInit(0.U(3.W))
    val next_state = Wire(UInt(3.W))
    
    state := next_state
    next_state := sIdle
    
    val in1_sign = Reg(Bool()) //FALSE:positive TRUE:negative
    val in2_sign = Reg(Bool()) //FALSE:positive TRUE:negative
    
    val in1 = RegInit(0.S(10.W))
    val in2 = RegInit(0.S(10.W))
    val op  = RegInit(0.U( 5.W))
    
    io.outcome := 0.S
    io.valid := state === sOut
    
    def sign(number : SInt , sign : Bool): SInt = Mux(sign === true.B , number * -1.S , number)
    
    when (state === sIdle){
        in1 := 0.S
        in2 := 0.S
        in1_sign := false.B
        in2_sign := false.B
        op := 10.U
        next_state := sIn1_sign
    }.elsewhen (state === sIn1_sign){
        when(io.key_in === sub){
            //printf("key_in = SUB \n")
            in1_sign := true.B
            next_state := sIn1_value
        }.elsewhen (io.key_in === add){
            //printf("key_in = ADD \n")
            in1_sign := false.B
            next_state := sIn1_value
        }.elsewhen (io.key_in < 10.U){ // while input 0~9
            //printf("key_in = NUMBER \n")
            in1 := io.key_in.asSInt
            next_state := sIn1_value
        }.elsewhen(io.key_in === eq){
            next_state := sOut
        }.otherwise{
            next_state := sIn1_sign
            printf("Wrong Input , please try again! \n")
        }
    }.elsewhen (state === sIn1_value){
        when (io.key_in < 10.U){
           in1 := (in1 << 3) + (in1 << 1) +  io.key_in.asSInt
           next_state := sIn1_value
        }.elsewhen(io.key_in ===add || io.key_in ===sub || io.key_in ===mul){
            op := io.key_in
            next_state := sIn2_sign
        }.elsewhen(io.key_in === eq){
            next_state := sOut
        }.otherwise{
            next_state := sIn1_value
            printf("Wrong Input , please try again! \n")
        }
    }.elsewhen (state === sIn2_sign){
        when(io.key_in === sub){
            //printf("key_in = SUB \n")
            in2_sign := true.B
            next_state := sIn2_value
        }.elsewhen (io.key_in === add){
            //printf("key_in = ADD \n")
            in2_sign := false.B
            next_state := sIn2_value
        }.elsewhen (io.key_in < 10.U){ // while input 0~9
            //printf("key_in = NUMBER \n")
            in2 := io.key_in.asSInt
            next_state := sIn2_value
        }.otherwise{
            next_state := sIn2_sign
            printf("Wrong Input , please try again! \n")
        }
    }.elsewhen (state === sIn2_value){
        when (io.key_in < 10.U){
           in2 := (in2 << 3) + (in2 << 1) +  io.key_in.asSInt
           next_state := sIn2_value
        }.elsewhen (io.key_in === eq){
            next_state := sOut
            //printf("Calculating....\n")
        }.otherwise{
            next_state := sIn2_value
            printf("Wrong Input , please try again! \n")
        }
    }.elsewhen (state === sOut){
        when(op === 10.U){
            io.outcome := sign(in1,in1_sign) + sign(in2,in2_sign)
        }.elsewhen (op === 11.U) {
            io.outcome := sign(in1,in1_sign) - sign(in2,in2_sign)
        }.elsewhen (op === 12.U){
            io.outcome := sign(in1,in1_sign) * sign(in2,in2_sign)
        }
        next_state := sSleep
    }.elsewhen(state === sSleep){
        next_state := sSleep
    }
    
    when(io.valid){
        printf("Outcome = %d\n",io.outcome)
    }
////////////     DEBUG BLOCK       ///////////////
//     printf("io.key_in = %d\n",io.key_in)
//     printf("in1 = %d\n",in1)
//     printf("in1_sign = %d\n",in1_sign)
//     printf("op = %d\n",op)
//     printf("in2 = %d\n",in2)
//     printf("in2_sign = %d\n",in2_sign)
//     printf("io.outcome = %d\n",io.outcome)
//     printf("state = %d \n",state)
//     printf("next_state = %d \n",next_state)
}