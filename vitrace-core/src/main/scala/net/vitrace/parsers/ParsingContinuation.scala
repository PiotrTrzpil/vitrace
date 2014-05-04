package net.vitrace.parsers

trait ParsingContinuation {
  def parse(line: String) : (ParseLineResult, Option[ParsingContinuation])
}
