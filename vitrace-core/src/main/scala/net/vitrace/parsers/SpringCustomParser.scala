package net.vitrace.parsers

import com.digitaldoodles.rex.{CharSet, Chars, Lit}
import org.joda.time.format.DateTimeFormat
import org.joda.time.LocalTime
import vitrace.LogLevel
import vitrace.LogLevel.LogLevel
import org.slf4j.{LoggerFactory, Logger}


class SpringCustomParser extends LogParser
{
   private val logger: Logger = LoggerFactory.getLogger(getClass)


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
      case "WARN" => LogLevel.Warn
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

   def parse(line : String, index : Int) : Option[LogLine]=
   {
      try
      {
         index match
         {
            case 0 => Some(parse(lineStandard2,line).getOrElse(null))
            case _ => Some(parse(lineAny,line).getOrElse(null))
         }
      }
      catch
         {
            case e: MatchError =>
               logger.warn("Illegal level string while parsing.", e)
               None
            case e: IllegalArgumentException =>
               logger.warn("Illegal time while parsing.", e)
               None
         }
   }

}