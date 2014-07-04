package net.vitrace.parsers

import scala.language.experimental.macros
import scala.util.parsing.combinator.RegexParsers
import scalaz.Scalaz._
import scala.util.parsing.combinator.Parsers

object LogParser
{
   val anyParser = new RawFallbackParser
   def any = anyParser
}

trait LogParser extends RegexParsers with Serializable
{
   val id : String
   val spanSize : Int
   val linesDeclaration : Vector[Parser[LogLine]]

   def toOption[T](result : ParseResult[T]) : Option[T] = {
      Option.apply(result.getOrElse[T](null.asInstanceOf[T]))
   }
  def createNew() : LogParser
  def parse(line : String) = createFirstContinuation.parse(line)

  def createFirstContinuation : ParsingContinuation = createContinuation(0).get

  def createContinuation(i : Int) : Option[ParsingContinuation] = {
    if (i < spanSize) {
      new ParsingContinuation {
        def parse(line: String) = {
          createNew().parse(line, i) match {
            case succ : ParseSuccess => (succ, createContinuation(i + 1))
            case x : ParseFailure => (x, None)
          }
        }
      }.some
    }
    else None

  }

  def parse(line : String, index : Int) : ParseLineResult =
  {
    val res = parse(linesDeclaration(index), line)

    res match {
      case x:Success[LogLine] => ParseSuccess(line, x.result)
      case f => ParseFailure(line, "No match")
    }
  }

  override def hashCode() = id.hashCode()

  override def equals(obj: scala.Any) = obj match {
    case that: LogParser => that.id.equals(this.id)
    case _ => false
  }

  override def toString = id
}