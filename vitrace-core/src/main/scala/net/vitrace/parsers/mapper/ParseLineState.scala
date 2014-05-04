package net.vitrace.parsers.mapper

import net.vitrace.parsers.{LogLine, LogParser, ParsingContinuation, ParseSuccess}

case class ParseLineState(groups:Vector[LineGroup]){


 // def merge(other:ParseLineState) =
}

case class LineGroup(logLines:Vector[ParseSuccess], parser:LogParser, cont: Option[ParsingContinuation])