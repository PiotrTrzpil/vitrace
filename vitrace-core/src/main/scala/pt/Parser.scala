import com.digitaldoodles.rex.Chars
import scala.util.matching.Regex

import scala.util.parsing.combinator.RegexParsers

class LoopParser extends RegexParsers
{
   override type Elem = Char
   def identifier: Regex = """[_\p{L}][_\p{L}\p{Nd}]*""".r
   def integer     = """(0|[1-9]\d*)""".r ^^ { _.toInt }
   def loop =
      "for"~identifier~"in"~integer~"to"~integer ^^
        { case f~variable~i~lBound~t~uBound => ForLoop(variable, lBound, uBound,uBound) }
  def statements = statement*
   def block = "{"~>statements<~"}"  ^^ { l => Block(l) }
   def statement : Parser[Statement] = loop | block

 //  def logLevel =
   def logLevel = """[_\p{L}][_\p{L}\p{Nd}]*""".r
   def threadName = """[_\p{L}][_\p{L}\p{Nd}]*""".r


   def line = logLevel~"["~threadName ^^
     { case logLevell~d~threadNamee => Line(logLevell, threadNamee) }

}

trait Statement
case class Block(statements : List[Statement]) extends Statement
case class ForLoop(variable: String, lowerBound:Int, upperBound: Int, statement:Int) extends Statement
case class Line(logLevell: String, threadNamee:String) extends Statement