package net.vitrace.parsers

import org.scalatest.FunSuite


import scala.language.experimental.macros


// This imports the basic constructors and predefined patterns.

import scala.util.parsing.combinator.RegexParsers
// This imports a single (as of Rex 0,7) implicit conversion that allows strings to be used
// as literals in Rex expressions.
//import com.digitaldoodles.rex.Implicits._
// This imports objects that contain further predefined patterns; see the API documentation for details.

class LogLineTest extends FunSuite with RegexParsers
{
   case class LineT1(message : String) extends LogLine{
      def toMap = Map("message" -> message)
   }
   case class LineT2(notes : String) extends LogLine{
      def toMap = Map("notes" -> notes)
   }





}