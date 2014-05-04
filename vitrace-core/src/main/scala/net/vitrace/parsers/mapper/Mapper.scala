package net.vitrace.parsers.mapper

import net.vitrace.parsers._
import scala.Some
import net.vitrace.parsers.ParseSuccess
import org.slf4j.{LoggerFactory, Logger}

class Mapper(parsers : Stream[LogParser]) {

  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def map(line: String) : ParseLineState = parsers.map(p => (p,p.parse(line))).collectFirst{
    case (parser, (result : ParseSuccess, continuationOpt)) =>
      ParseLineState(Vector(LineGroup(Vector(result), parser, continuationOpt)))

  }.getOrElse(throw new Exception("none of the parsers succeeded."))


  def reduce(a:ParseLineState, b:ParseLineState) : ParseLineState = {
    val lastGroup = a.groups.last
    val nextGroup = b.groups.head

    lastGroup.cont match {
      case Some(continuation) if nextGroup.parser == LogParser.any =>
        val parseNext = continuation.parse(nextGroup.logLines.head.sourceLine)
        parseNext match {
          case (succ @ ParseSuccess(_, logLine), contOpt) =>
            val merged = ParseLineState(a.groups.dropRight(1) :+
              LineGroup(lastGroup.logLines :+ succ, lastGroup.parser, contOpt))
            if (b.groups.tail.isEmpty) {
              merged
            } else {
              reduce(
                merged,
                ParseLineState(b.groups.tail))
            }
          case (ParseFailure(_, errMessage), _) =>
            logger.warn("Expecting next parsing success, but got none: "+errMessage)
            ParseLineState(a.groups ++ b.groups)
        }
      case Some(continuation) if nextGroup.parser != LogParser.any =>
        logger.warn("Conflict: previous parser expected consecuting line match ")
        ParseLineState(a.groups ++ b.groups)
      // ^- controversial - this may unjustly resolve some conflicts
      case None =>
        ParseLineState(a.groups ++ b.groups)

    }
  }


}
