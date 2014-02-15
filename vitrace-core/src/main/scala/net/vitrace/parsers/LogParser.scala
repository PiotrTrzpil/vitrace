package net.vitrace.parsers
import scala.util.parsing.combinator.RegexParsers


object LogParser
{
   val anyParser = new AnyParser
   def any = anyParser
}
trait LogParser extends RegexParsers
{
   val spanSize : Int
   def parse(line : String, index : Int) : Option[LogLine]

   def toOption[T](result : ParseResult[T]) : Option[T] = {
      Option.apply(result.getOrElse[T](null.asInstanceOf[T]))
   }
}