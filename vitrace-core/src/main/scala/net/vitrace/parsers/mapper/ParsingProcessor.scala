package net.vitrace.parsers.mapper

import net.vitrace.parsers._
import scala.Some
import net.vitrace.parsers.ParseSuccess
import org.slf4j.{LoggerFactory, Logger}

class ParsingProcessor(parsers : Stream[()=>LogParser]) extends Serializable{

  implicit val maa = new ParserFactory(parsers)

  private val logger: Logger = LoggerFactory.getLogger(getClass)

  def map(line: String) : Vector[LineGroup] = parsers.map(p => (p,p().parse(line))).collectFirst{
    case (parser, (result : ParseSuccess, continuationOpt)) =>
      Vector(LineGroup(Vector(result), parser().id, continuationOpt))

  }.getOrElse(throw new Exception("none of the parsers succeeded."))


  def reduce(a:Vector[LineGroup], b:Vector[LineGroup]) : Vector[LineGroup] = {
    val lastGroup = a.last
    val nextGroup = b.head

    lastGroup.cont match {
      case Some(continuation) if nextGroup.parser == "Any" =>
        val parseNext = continuation.parse(nextGroup.logLines.head.sourceLine)
        parseNext match {
          case (succ @ ParseSuccess(_, logLine), contOpt) =>
            val merged = a.dropRight(1) :+
              LineGroup(lastGroup.logLines :+ succ, lastGroup.parser, contOpt)
            if (b.tail.isEmpty) merged
            else reduce(merged,  b.tail)

          case (ParseFailure(_, errMessage), _) =>
            logger.warn("Expecting next parsing success, but got none: "+errMessage)
            a ++ b
        }
      case Some(continuation) if nextGroup.parser != "Any" =>
        logger.warn("Conflict: previous parser expected consecuting line match ")
        a ++ b
      // ^- controversial - this may unjustly resolve some conflicts
      case None => a ++ b

    }
  }


}
