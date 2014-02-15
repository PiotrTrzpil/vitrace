package net.vitrace.parsers

import com.digitaldoodles.rex.Chars
import pt.LogLine


class AnyParser extends LogParser
{
   def anyChars = (Chars.Any *> 0).r

   def lineStandard2 = anyChars ^^
     { case message => LineSimple(message) }

   case class LineSimple( message:String) extends LogLine
   {
   }

   def parse(s : String, index:Int) : Option[LogLine]=
   {
      Some(parse(lineStandard2,s ).getOrElse(null))
   }

   val spanSize = 1
}