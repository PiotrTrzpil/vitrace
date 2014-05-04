package net.vitrace.parsers.mapper

import net.vitrace.parsers.LogParser

class RawLinesMerger {

  def map(group: LineGroup) = {
    Vector(group)
  }
  def reduce(a: Vector[LineGroup], b: Vector[LineGroup]) = {
    (a, b) match {
      case (vect, Vector(g2))
        if vect.last.parser == LogParser.any && g2.parser == LogParser.any =>
          vect.updated(vect.length-1, LineGroup(vect.last.logLines ++ g2.logLines, g2.parser, None))
      case _ => a ++ b
    }
  }
}
