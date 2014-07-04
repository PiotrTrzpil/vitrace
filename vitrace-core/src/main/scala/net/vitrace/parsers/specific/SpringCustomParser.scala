package net.vitrace.parsers

import com.digitaldoodles.rex.{CharSet, Chars, Lit}
import org.joda.time.format.DateTimeFormat
import org.joda.time.LocalTime
import org.slf4j.{LoggerFactory, Logger}
import net.vitrace.LogLevel.LogLevel
import net.vitrace.LogLevel
import org.parboiled.scala.Parser
import sun.org.mozilla.javascript.internal.ast.AstNode
import org.parboiled.errors._
import org.apache.avro.data.Json
import org.parboiled.scala.rules.Rule0
import scala.xml.dtd.ANY
import org.parboiled.scala._

class SpringCustomParser extends LogParser
{
   val id = "SpringCustom"
   val spanSize = 1

   def testt = (Lit("[user:").<< +~ (Chars.Any *< 0) +~ Lit("]").>>).r
   def anyChars = (Chars.Any *> 0).r
   def anyCharsButBracket = ((Chars.Any - CharSet("]")) *> 0).r
   def logLevel = (Chars.Alphabetic *> 1).r
   def threadName = (Chars.Alphanumeric *> 1).r
   def date = (Chars.Digit *> (4,4) +~ Lit("-") +~ Chars.Digit *> (2,2) +~ Lit("-") +~ Chars.Digit *> (2,2)).r
   def time = (Chars.Digit *> (2,2) +~ Lit(":") +~ Chars.Digit *> (2,2) +~ Lit(":") +~ Chars.Digit *> (2,2) +~ Lit(".") +~ Chars.Digit *> (3,3)).r
   def clas = (Chars.Alphabetic.charSet(".") *> 1).r
   def ip = (Lit("[ip:").<< +~ (Chars.Any*< 0) +~ Lit("]").>>).r

   //INFO  15:18:14.797 o.s.s.c.SpringSecurityCoreVersion [ip:123] [user:] - You are running with Spring Security Core 3.1.4.RELEASE
   def lineStandard = logLevel~time~clas~("[ip:"~>anyCharsButBracket<~"]")~(("[user:"~>anyCharsButBracket<~"]")<~"-")~anyChars ^^
     { case logLevel~time~loggerName~ip~user~message => LineFull(logLevel, time, loggerName, ip, user, message) }

   case class LineFull(level: String, time:String, loggerName:String, ip:String,
                       user:String, message:String) extends LogLine {
      def toMap = Map("level" -> level, "time" -> time, "loggerName" -> loggerName, "ip" -> ip,
         "user" -> user, "message" -> message)
   }

   val linesDeclaration = Vector(lineStandard)
  override def createNew() = new SpringCustomParser
}

class SpringCustomParser2 extends Parser
{

  sealed abstract class AstNode
  case class ObjectNode(members: List[MemberNode]) extends AstNode
  case class MemberNode(key: String, value: AstNode) extends AstNode
  case class ArrayNode(elements: List[AstNode]) extends AstNode
  case class StringNode(text: String) extends AstNode
  case class NumberNode(value: BigDecimal) extends AstNode
  case object True extends AstNode
  case object False extends AstNode
  case object Null extends AstNode

  // the root rule
  def Json = rule { WhiteSpace }

//  def Text = rule { "\"" ~ zeroOrMore(Character) ~> StringNode ~ "\" " }
//
//// def Mdc = rule { "[ " ~ Text ~ ":" ~ "] " ~~> ArrayNode }
//
//
//  def Character = rule { EscapedChar | NormalChar }
//
//  def EscapedChar = rule { "\\" ~ (anyOf("\"\\/bfnrt") | Unicode) }
//
//  def NormalChar = rule { !anyOf("\"\\") ~ ANY }
//
//  def Unicode = rule { "u" ~ HexDigit ~ HexDigit ~ HexDigit ~ HexDigit }
//
//  def HexDigit = rule { "0" - "9" | "a" - "f" | "A" - "Z" }

  override implicit def toRule(string: String) =
    if (string.endsWith(" "))
      str(string.trim) ~ WhiteSpace
    else
      str(string)

  def WhiteSpace: Rule0 = rule { zeroOrMore(anyOf(" \n\r\t\f")) }

  def parseJson(json: String): AstNode = {
    val parsingResult = ReportingParseRunner(Json).run(json)
    parsingResult.result match {
      case Some(astRoot) => astRoot
      case None => throw new ParsingException("Invalid JSON source:\n" +
        ErrorUtils.printParseErrors(parsingResult))
    }
  }
}