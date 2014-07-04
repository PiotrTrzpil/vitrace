package net.vitrace.parsers

import com.digitaldoodles.rex.{Chars, Lit}
import org.joda.time.format.DateTimeFormat
import org.joda.time.DateTime
import java.util.Locale
import org.slf4j.{LoggerFactory, Logger}
import net.vitrace.LogLevel.LogLevel
import net.vitrace.LogLevel
import scala.util.parsing.combinator.RegexParsers


class TomcatParser extends LogParser
{
   val id = "Tomcat"
   val spanSize = 2

   def anyChars = (Chars.Any *> 0).r
   def anyCharsEndingWithPMorAM =  ((Chars.Any *< 1) +~ (Lit("AM")|Lit("PM"))).r
   def alphanumerics = (Chars.Alphabetic *> 1).r
   def nonWhitespace  = (Chars.NonWhitespace *> 1).r


   //"sie 09, 2013 5:16:45 PM org.apache.catalina.startup.HostConfig deleteRedeployResources",
   //"INFO: Undeploying context [/cms-front]",
   def lineFirst = anyCharsEndingWithPMorAM~nonWhitespace~nonWhitespace ^^
     { case dateTime~loggerName~methodName => LineFirst(dateTime, loggerName, methodName) }

   def lineSecond = (alphanumerics<~":")~anyChars ^^
     { case level~message => LineSecond(level, message) }



   case class LineFirst( dateTime:String, loggerName:String, methodName:String) extends LogLine {
      def toMap = Map("dateTime" -> dateTime, "loggerName" -> loggerName, "methodName" -> methodName)
   }
   case class LineSecond( level:String, message:String) extends LogLine {
      def toMap = Map("level" -> level, "message" -> message)
   }

   val linesDeclaration = Vector(lineFirst, lineSecond)

  override def createNew() = new TomcatParser
}