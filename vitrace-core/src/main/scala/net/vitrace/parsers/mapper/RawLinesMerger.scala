package net.vitrace.parsers.mapper

import net.vitrace.parsers.LogParser

class RawLinesMerger extends Serializable {

  def map(group: LineGroup) = {
    Vector(group)
  }
  def reduce(a: Vector[LineGroup], b: Vector[LineGroup]) = {
    (a, b) match {
      case (vect, Vector(g2))
        if vect.last.parser == "Any" && g2.parser == "Any" =>
          vect.updated(vect.length-1, LineGroup(vect.last.logLines ++ g2.logLines, g2.parser, None))
      case _ => a ++ b
    }
  }
}
