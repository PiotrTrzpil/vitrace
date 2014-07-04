package net.vitrace.parsers

import org.scalatest.FunSuite



import com.digitaldoodles.rex._

import scala.util.parsing.combinator.RegexParsers

class Test1 extends FunSuite with RegexParsers
{




   case class Line4(message : String) extends LogLine{
      def toMap = Map("message" -> message)
   }


   test("asMap")
   {
      val sd : LogLine = Line4("tada")
      val line = sd
      val map = line.toMap
     // assert(map.isEmpty === false)

   }


   test("t3")
   {
      // *>1 means 1 or more, greedily (as many times as possible.)
      val posInt = CharRange('0','9')*>1
      // Lit means literal. The "-" is automatically converted to a literal.
      val sign = Lit("+")|Lit("-")
      // +~ represents concatenation, ? means an element is optional.
      val floatPat = sign.? +~ posInt +~ (Lit(".") +~ posInt).?
      // "name" creates named groups for extracting information from matches.
      val complex = floatPat.name("re") +~ sign.name("op") +~ floatPat.name("im") +~ Lit("i")

   /*   println(complex.r)
      val res: Boolean = complex ~~= "-423.34+342i"
      println(res)*/



   }
}