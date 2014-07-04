package net.vitrace.parsers

import com.digitaldoodles.rex.Chars
import net.vitrace.parsers
import scalaz.Scalaz
import Scalaz._

class RawFallbackParser extends LogParser
{
  val id = "Any"

   def anyChars = (Chars.Any *> 0).r

   def lineStandard = anyChars ^^
     { case message => LineSimple(message) }

   case class LineSimple( message:String) extends LogLine
   {
      def toMap = Map("message" -> message)
   }

   val linesDeclaration = Vector(lineStandard)
   val spanSize = 1
   override def createNew() = new RawFallbackParser
}