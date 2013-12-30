package net.vitrace.parsers

import scala.collection.mutable.ListBuffer

class LogEntry()
{
   var logLines : ListBuffer[LogLine] = new ListBuffer[LogLine]

   override def toString() : String =
   {
      "LogEntry: lines count: "+logLines.size
   }

}
