package net.vitrace.parsers

import org.scalatest.FunSuite


import scala.language.experimental.macros


// This imports the basic constructors and predefined patterns.

import scala.util.parsing.combinator.RegexParsers

class LogLineTest extends FunSuite with RegexParsers
{
   case class LineT1(message : String) extends LogLine{
      def toMap = Map("message" -> message)
   }
   case class LineT2(notes : String) extends LogLine{
      def toMap = Map("notes" -> notes)
   }


}