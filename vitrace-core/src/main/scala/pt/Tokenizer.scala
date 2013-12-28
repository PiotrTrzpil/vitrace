package pt

import scala.util.matching.Regex
import scala.util.matching.Regex.Match
import scala.reflect.runtime.universe._


class Tokenizer()
{
   //patterns.

   val loglevelRegex: Regex = """\w+""".r
   val dateP1: Regex = """(\d\d\d\d)-(\d\d)-(\d\d)""".r
   val dateP2 = new scala.util.matching.Regex("""(\d\d\d\d)-(\d\d)-(\d\d)""", "year", "month", "day")

   val dateP1(year, month, day) = "2011-07-15"

   // val dateP1(year, month, day) = "Date 2011-07-15" // throws an exception at runtime

   val copyright: String = dateP1 findFirstIn "Date of this document: 2011-07-15" match {
      case Some(dateP1(`year`, `month`, `day`)) => "Copyright "+year
      case None                           => "No copyright"
   }

   def tokenize(line: String, remainingPatterns: List[LineToken], acc: LogLineOLd = new LogLineOLd) : LogLineOLd =
   {
      if(remainingPatterns.isEmpty)
      {
         acc
      }
      else
      {
         val pattern = remainingPatterns.head
         val logLine = acc
         val field: TermSymbol = typeOf[LogLineOLd].declaration(pattern.name : TermName).asTerm
         val mirror: FieldMirror = runtimeMirror(logLine.getClass.getClassLoader).reflect(logLine).reflectField(field)
         val (value: Any, end: Int) = pattern.fingValue(line)
         mirror.set(value)
         tokenize(line.substring(end), remainingPatterns.tail, logLine)
      }
   }
}
