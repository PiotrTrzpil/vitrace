package net.vitrace.parsers

import com.digitaldoodles.rex.Chars
import net.vitrace.parsers
import scalaz.Scalaz
import Scalaz._

class AnyParser extends LogParser
{
  val id = "Any"

   def anyChars = (Chars.Any *> 0).r

   def lineStandard2 = anyChars ^^
     { case message => LineSimple(message) }

   case class LineSimple( message:String) extends LogLine
   {
      def toMap = Map("message" -> message)

   }

   def parse(line : String, index:Int) : ParseLineResult=
   {
      ParseSuccess(line,parse(lineStandard2,line ).get)
   }

   val spanSize = 1
}