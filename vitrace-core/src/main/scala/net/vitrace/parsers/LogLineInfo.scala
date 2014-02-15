package net.vitrace.parsers

import pt.LogLine

trait LogLineInfo {
   def asMap : Map[String, Any]


}

class SingleLineInfo(line : LogLine) extends LogLineInfo
{
   def asMap = line.toMap
}
