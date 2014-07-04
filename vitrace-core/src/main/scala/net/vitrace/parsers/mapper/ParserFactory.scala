package net.vitrace.parsers.mapper

import net.vitrace.parsers.LogParser

class ParserFactory(parsers : Stream[()=>LogParser]) extends Serializable{

  val parserMap = parsers.map(f => (f().id, f)).toMap

  def createNew(id:String) = parserMap(id)()

}
