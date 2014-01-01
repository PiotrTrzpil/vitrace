package net.vitrace

import scala.io.BufferedSource

class LogfileReader
{

   def read(path:String ) : Iterator[String] =
   {
      val source: BufferedSource = scala.io.Source.fromFile(path)
      val lines: Iterator[String] = source.getLines
      source.close()
      lines
   }
}
