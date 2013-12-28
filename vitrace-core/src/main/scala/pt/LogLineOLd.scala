package pt

import java.lang.reflect.Field
import scala.util.matching.Regex.Match

class LogLineOLd(var logLevel:String = "",
              var time:String = "",
              var thread:String = "")
{
   override def toString() =
   {
      "logLevel:"+logLevel+" thread:"+thread+" time:"+time
   }


}
