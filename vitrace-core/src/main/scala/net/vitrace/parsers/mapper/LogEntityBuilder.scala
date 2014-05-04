package net.vitrace.parsers.mapper

import net.vitrace.parsers.LogParser

//class AttributesBag
//
//case class LogEntity(attributes: Map[String, Any],
//                attachedLines: Vector[String],
//                sourceLines: Vector[String])
//
//class LogEntityBuilder {
//
//  def map(group: LineGroup) = {
//    LogEntity(
//      group.logLines.flatMap(_.logLine.toMap).toMap,
//      Vector(),
//      group.logLines.map(_.sourceLine))
//  }
//  def reduce(a: LogEntity, b: LogEntity) = {
//
//    (a, b) match {
//      case (vect, Vector(g2))
//        if vect.last.parser == LogParser.any && g2.parser == LogParser.any =>
//        vect.updated(vect.length-1, LineGroup(vect.last.logLines ++ g2.logLines, g2.parser, None))
//      case _ => a ++ b
//    }
//  }

//}
