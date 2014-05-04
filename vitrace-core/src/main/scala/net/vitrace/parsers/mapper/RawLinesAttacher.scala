package net.vitrace.parsers.mapper

import net.vitrace.parsers.LogParser

//case class LineGroupAttached(group: LineGroup, attachedLines: Vector[String])
//
//class RawLinesAttacher {
//
//   def map(group: LineGroup) = {
//     Vector(LineGroupAttached(group, Vector()))
//   }
//   def reduce(a: Vector[LineGroupAttached], b: Vector[LineGroupAttached]) = {
//     (a, b) match {
//       case (vect, Vector(g2))
//         if vect.last.parser == LogParser.any && g2.parser == LogParser.any =>
//           vect.updated(vect.length-1, LineGroup(vect.last.logLines ++ g2.logLines, g2.parser, None))
//       case _ => a ++ b
//     }
//   }
// }
