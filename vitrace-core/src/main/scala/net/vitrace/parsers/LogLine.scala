package net.vitrace.parsers



trait LogLine extends Serializable
{
   def toMap: Map[String, Any]

}

object EmptyLine extends LogLine
{
   def toMap = Map()
}