package net.vitrace.parsers

import scala.collection.mutable.ListBuffer

import scala._
import scala.Some
import scala.annotation.tailrec

class IncompleteParsing(offset : Int) {

}

class EntriesBuilder
{
   def and(x:Boolean,y:Boolean) : Boolean = if(x) y else false

//
//   def parseFragment(lines : List[String], index: Int, parser : LogParser) : LogLine = lines match {
//
//      case List() => EmptyLine
//      case a :: ax => parser.parse(a, index) match {
//         case Some(logline) => logline merge parseFragment(ax, index+1, parser)
//         case None => EmptyLine
//      }
//
//   }
//
//  //
//   def parseLines(lines : List[String], offset: Int, parsers: List[LogParser])
//         : Seq[(LogLine, Option[IncompleteParsing])] = {
//
//     @tailrec
//     def loop(lines : List[String], offset: Int, result : Seq[(LogLine, Option[IncompleteParsing])])
//            : Seq[(LogLine, Option[IncompleteParsing])] = {
//        if (lines.isEmpty) {
//           result
//        }
//        else {
//           val winner = parsers.collectFirst({case x if x.parse(lines.head, 0) != None => x}).get
//           val slice = lines.slice(0, winner.spanSize)
//           val linesUnscanned = winner.spanSize - slice.length
//           val incomplete = if (linesUnscanned > 0) Some(new IncompleteParsing(offset)) else None
//           val logLine: LogLine = parseFragment(lines.slice(0, winner.spanSize), 0, winner)
//
//           val newLines = lines.drop(slice.length)
//           loop(newLines, offset, result :+ (logLine, incomplete))
//        }
//
//     }
//
//     loop(lines, offset, List[(LogLine, Option[IncompleteParsing])]())
//
//   }
//
//   def parseText(text : List[String], parsers: List[LogParser]) : Seq[LogEntry] =
//   {
//
//      Some(2).copy()
//
//      val any = LogParser.any
//      val entries = new ListBuffer[LogEntry]
//      var lastWinner : LogParser = any
//      var correntIndex = 0
//
//      var currentEntry = new LogEntry()
//      for ( line <- text)
//      {
//         val winner = parsers.collectFirst({case x if x.parse(line, 0) != None => x}).get
//
//         val s: LogLine = if (winner == any)
//         {
//            if(lastWinner == any)
//            {
//               currentEntry = new LogEntry
//               entries += currentEntry
//               any.parse(line, 0).get
//            }
//            else {
//               correntIndex = correntIndex + 1
//               lastWinner.parse(line, correntIndex) match {
//                  case Some(lup) => lup
//                  case _ =>
//                     lastWinner = any
//                     currentEntry = new LogEntry
//                     entries += currentEntry
//                     any.parse(line, 0).get
//               }
//            }
//         }
//         else
//         {
//            correntIndex = 0
//            lastWinner = winner
//            currentEntry = new LogEntry()
//            entries += currentEntry
//            winner.parse(line, correntIndex).get
//         }
//         currentEntry.logLines += s
//      }
//      entries
//   }
}
