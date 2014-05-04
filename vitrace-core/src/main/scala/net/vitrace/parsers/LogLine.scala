package net.vitrace.parsers



trait LogLine
{
   def toMap: Map[String, Any]

}

object EmptyLine extends LogLine
{
   def toMap = Map()
}