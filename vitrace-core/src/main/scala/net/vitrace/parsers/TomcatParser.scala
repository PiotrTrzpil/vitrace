package net.vitrace.parsers

import com.digitaldoodles.rex.{Chars, Lit}
import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime
import vitrace.LogLevel
import vitrace.LogLevel.LogLevel
import vitrace.parsers.{LogLine, LogParser}
import java.util.Locale


class TomcatParser extends LogParser
{
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

   case class LineFirst( dateTime:DateTime, loggerName:String, methodName:String) extends LogLine
   {
      override def toMap : Map[String, Any] =
      {
         Map(
            "dateTime" -> dateTime,
            "methodName" -> methodName,
            "loggerName" -> loggerName
         )
      }
   }
   case class LineSecond( level:LogLevel, message:String) extends LogLine
   {
      override def toMap : Map[String, Any] =
      {
         Map(
            "level" -> level,
            "message" -> message
         )
      }
   }
   def parseLevel(level : String) = level match{
      case "DEBUG" => LogLevel.Debug
      case "INFO" => LogLevel.Info
      case "WARN" => LogLevel.Warning
      case "ERROR" => LogLevel.Error
      case "SEVERE" => LogLevel.Error
      case "TRACE" => LogLevel.Trace
   }
   val dateFormat = DateTimeFormat.forPattern("MMM dd, yyyy hh:mm:ss aa").withLocale(Locale.forLanguageTag("pl-PL"))

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
      parse(lineFirst,s )match {
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