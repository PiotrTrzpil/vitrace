package pt.parsers

import scala.util.parsing.combinator.RegexParsers
trait LogLine
{
   def toMap : Map[String, Any]
}

object LogParser
{
   val anyParser = new AnyParser
   def any = anyParser
}
trait LogParser extends RegexParsers
{
   def parseConsecuting(line: String) : Option[LogLine]

   def parse(line : String) : Option[LogLine]


}