package net.vitrace.parsers


/*

class Log4NetStandardParser extends LogParser
{
   def logLevel = (Chars.Alphabetic *> 1).r
   def threadName = (Chars.Alphanumeric *> 1).r
   def date = (Chars.Digit *> (4,4) +~ Lit("-") +~ Chars.Digit *> (2,2) +~ Lit("-") +~ Chars.Digit *> (2,2)).r
   def time = (Chars.Digit *> (2,2) +~ Lit(":") +~ Chars.Digit *> (2,2) +~ Lit(":") +~ Chars.Digit *> (2,2) +~ Lit(",") +~ Chars.Digit *> (3,3)).r
   def runningMs = (Chars.Digit *> 1).r ^^ {_.toInt}

   def anyChars = (Chars.Any *> 0).r
   case class Line(logLevel: String, threadName:String, date:String, time:String, runningMs:Int) extends LogLine
   {
      def toMap =
      {
         Map("logLevel" -> logLevel,
            "threadName" -> threadName,
            "date" -> 26)
      }
   }
   val dateParser = new DateTimeParser(DateTimeFormat.forPattern("yyyyMMdd"))

   def lineStandard = logLevel~("["~>threadName<~"]"<~"-")~date~time~(runningMs<~"ms") ^^
     { case logLevel~threadName~date~time~runningMs => Line(logLevel, threadName, date, time, runningMs) }


   def parseDate(str : String) = dateParser.parse(str, Locale.forLanguageTag("pl-PL"))

   def lineAny = anyChars ^^
     { case message => Line3(message) }

   case class Line3( message:String) extends LogLine
   {
      override def toMap : Map[String, Any] =
      {
         Map(
            "message" -> message
         )
      }
   }


   def parse(line : String, index : Int) : Option[LogLine]= {
      index match {
         case 0 => parse(lineStandard,line)
         case _ => parse(lineAny,line)
      }
   }
}
*/