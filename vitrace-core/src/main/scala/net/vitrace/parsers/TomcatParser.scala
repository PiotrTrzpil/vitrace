package net.vitrace.parsers

import com.digitaldoodles.rex.{Chars, Lit}
import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime
import vitrace.LogLevel
import vitrace.LogLevel.LogLevel
import java.util.Locale
import org.slf4j.{LoggerFactory, Logger}

class TomcatParser extends LogParser
{
   private val logger: Logger = LoggerFactory.getLogger(getClass)

   def anyChars = (Chars.Any *> 0).r
   def anyCharsEndingWithPMorAM =  ((Chars.Any *< 1) +~ (Lit("AM")|Lit("PM"))).r
   def alphanumerics = (Chars.Alphabetic *> 1).r
   def nonWhitespace  = (Chars.NonWhitespace *> 1).r

   //"sie 09, 2013 5:16:45 PM org.apache.catalina.startup.HostConfig deleteRedeployResources",
   //"INFO: Undeploying context [/cms-front]",
   def lineFirst = anyCharsEndingWithPMorAM~nonWhitespace~nonWhitespace ^^
     { case dateTime~loggerName~methodName => LineFirst(dateFormat.parseDateTime(dateTime), loggerName, methodName) }

   def lineSecond = (alphanumerics<~":")~anyChars ^^
     { case level~message => LineSecond(parseLevel(level), message) }

   def lineAny = anyChars ^^
     { case message => LineBasic(message) }

   case class LineFirst( dateTime:DateTime, loggerName:String, methodName:String) extends LogLine
   case class LineSecond( level:LogLevel, message:String) extends LogLine
   case class LineBasic( message:String) extends LogLine

   val dateFormat = DateTimeFormat.forPattern("MMM dd, yyyy hh:mm:ss aa").withLocale(Locale.forLanguageTag("pl-PL"))

   def parseLevel(level : String) = level match{
      case "TRACE" => LogLevel.Trace
      case "DEBUG" => LogLevel.Debug
      case "INFO" => LogLevel.Info
      case "WARN" => LogLevel.Warn
      case "ERROR" => LogLevel.Error
      case "SEVERE" => LogLevel.Error
   }

   def parse(line : String, index : Int) : Option[LogLine] =
   {
      try
      {
         index match {
            case 0 => Some(parse(lineFirst,line).getOrElse(null))
            case 1 => Some(parse(lineSecond,line).getOrElse(null))
            case _ => Some(parse(lineAny,line).getOrElse(null))
         }
      }
      catch {
         case e:IllegalArgumentException =>
            logger.warn("Illegal date while parsing.")
            None
         case e: MatchError =>
            logger.warn("Illegal level string while parsing.", e)
            None
      }
   }

}