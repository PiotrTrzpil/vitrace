package net.vitrace.parsers

import com.digitaldoodles.rex.{CharSet, Chars, Lit}
import org.joda.time.format.DateTimeFormat
import org.joda.time.LocalTime
import org.slf4j.{LoggerFactory, Logger}
import net.vitrace.LogLevel.LogLevel
import net.vitrace.LogLevel



class SpringCustomParser extends LogParser
{
   private val logger: Logger = LoggerFactory.getLogger(getClass)

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
   def lineStandard2 = logLevel~time~clas~("[ip:"~>anyCharsButBracket<~"]")~(("[user:"~>anyCharsButBracket<~"]")<~"-")~anyChars ^^
     { case logLevel~time~loggerName~ip~user~message => LineFull(parseLevel(logLevel), timeFormat.parseLocalTime(time), loggerName, ip, user, message) }

   case class LineFull(level: LogLevel, time:LocalTime, loggerName:String, ip:String,
                       user:String, message:String) extends LogLine {
      def toMap = Map("level" -> level, "time" -> time, "loggerName" -> loggerName, "ip" -> ip,
         "user" -> user, "message" -> message)
   }



   def parseLevel(level : String) = level match{
      case "DEBUG" => LogLevel.Debug
      case "INFO" => LogLevel.Info
      case "WARN" => LogLevel.Warn
      case "ERROR" => LogLevel.Error
      case "TRACE" => LogLevel.Trace
   }

   val timeFormat = DateTimeFormat.forPattern("HH:mm:ss.SSS")



   def parse(line : String, index : Int) : ParseLineResult =
   {
      try
      {
         index match
         {
            case 0 =>
              val parsed = parse(lineStandard2,line)
              parsed match {
                case x:Success[LogLine] =>ParseSuccess(line, x.result)
                case f => ParseFailure(line, "No match")
              }
         }
      }
      catch
         {
            case e: MatchError =>
              ParseFailure(line, e.getMessage())
            case e: IllegalArgumentException =>
               ParseFailure(line, "Illegal time while parsing.")
         }
   }

}