package net.vitrace.parsers

import com.digitaldoodles.rex.{Chars, Lit}
import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime
import java.util.Locale
import org.slf4j.{LoggerFactory, Logger}
import net.vitrace.LogLevel.LogLevel
import net.vitrace.LogLevel


class TomcatParser extends LogParser
{
   private val logger: Logger = LoggerFactory.getLogger(getClass)

  val id = "Tomcat"
   val spanSize = 2

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


   case class LineFirst( dateTime:DateTime, loggerName:String, methodName:String) extends LogLine {
      def toMap = Map("dateTime" -> dateTime, "loggerName" -> loggerName, "methodName" -> methodName)
   }
   case class LineSecond( level:LogLevel, message:String) extends LogLine {
      def toMap = Map("level" -> level, "message" -> message)
   }

   val dateFormat = DateTimeFormat.forPattern("MMM dd, yyyy hh:mm:ss aa").withLocale(Locale.forLanguageTag("pl-PL"))

   def parseLevel(level : String) = level match{
      case "TRACE" => LogLevel.Trace
      case "DEBUG" => LogLevel.Debug
      case "INFO" => LogLevel.Info
      case "WARN" => LogLevel.Warn
      case "ERROR" => LogLevel.Error
      case "SEVERE" => LogLevel.Error
   }


   def parse(line : String, index : Int) : ParseLineResult =
   {
      try
      {
         val res = index match {
           case 0 => parse(lineFirst, line)
           case 1 => parse(lineSecond, line)
         }

         res match {
           case x:Success[LogLine] => ParseSuccess(line, x.result)
           case f => ParseFailure(line, "No match")
         }
      }
      catch {
         case e:IllegalArgumentException =>
            ParseFailure(line, "Illegal date while parsing.")
         case e: MatchError =>
            ParseFailure(line, e.getMessage())
      }
   }

}