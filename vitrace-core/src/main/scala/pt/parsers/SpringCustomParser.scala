package pt.parsers

import com.digitaldoodles.rex.{CharSet, Chars, Lit}
import org.springframework.format.datetime.joda.DateTimeParser
import org.joda.time.format.DateTimeFormat
import org.joda.time.LocalTime
import pt.LogLevel.LogLevel
import pt.LogLevel
import pt.LogLevel.LogLevel


class SpringCustomParser extends LogParser
{
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
   def lineStandard2 = logLevel~time~clas~("[ip:"~>anyCharsButBracket<~"]")~(("[user:"~>anyCharsButBracket<~"]")<~"-")~anyChars ^^
     { case logLevel~time~loggerName~ip~user~message => LineFull(parseLevel(logLevel), timeFormat.parseLocalTime(time), loggerName, ip, user, message) }

   case class LineFull(level: LogLevel, time:LocalTime, loggerName:String, ip:String, user:String, message:String) extends LogLine
   {
      override def toMap : Map[String, Any] =
      {
         Map(
            "logLevel" -> level,
            "time" -> time,
            "loggerName" -> loggerName,
            "message" -> message
         )
      }
   }

   def parseLevel(level : String) = level match{
      case "DEBUG" => LogLevel.Debug
      case "INFO" => LogLevel.Info
      case "WARN" => LogLevel.Warning
      case "ERROR" => LogLevel.Error
      case "TRACE" => LogLevel.Trace
   }

   val timeFormat = DateTimeFormat.forPattern("HH:mm:ss.SSS")

   def lineAny = anyChars ^^
     { case message => LineBasic(message) }

   case class LineBasic( message:String) extends LogLine
   {
      def toMap =
      {
         Map(
            "message" -> message
         )
      }
   }

   def parse(s : String) : Option[LogLine]= {
      parse(lineStandard2,s )match {
         case Success(lup,_) => Some(lup)
         case x => None
      }
   }
   def parseConsecuting(line: String) : Option[LogLine] =
   {
      parse(lineAny,line )match {
         case Success(lup,_) => Some(lup)
         case x => None
      }
   }
}