package net.vitrace.parsers

trait ParsingContinuation extends Serializable{
  def parse(line: String) : (ParseLineResult, Option[ParsingContinuation])
}
