package pt.parsers

import com.digitaldoodles.rex.Chars


class AnyParser extends LogParser
{
   def anyChars = (Chars.Any *> 0).r

   def lineStandard2 = anyChars ^^
     { case message => LineSimple(message) }

   case class LineSimple( message:String) extends LogLine
   {
      override def toMap : Map[String, Any] =
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
      parse(lineStandard2,line )match {
         case Success(lup,_) => Some(lup)
         case x => None
      }
   }
}