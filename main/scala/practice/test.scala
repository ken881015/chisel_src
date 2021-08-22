package practice 

import chisel3._
import chisel3.util._

class Tile extends Module {
  val width = params[Int]("width")
}

object Tile {
  val parameters = Parameters.empty
  val tile_parameters = parameters.alter(
    (key,site,here,up) => {
	  case "width" => 64
	}
  )
  def main(args:Array[String]) = {
    chiselMain (args,()=>Module(new Tile)(Some(tile_parameters)))
  }
}
