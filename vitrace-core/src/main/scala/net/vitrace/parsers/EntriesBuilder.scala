package net.vitrace.parsers

import scala.collection.mutable.ListBuffer

class EntriesBuilder
{
   def and(x:Boolean,y:Boolean) : Boolean = if(x) y else false


//   def ss(lines : List[String], index: Int, parser : LogParser) : List[LogLine]= lines match {
//      case a :: ax => parser.parse(a, index) match {
//         case Some(logline) => logline merge ss(ax, index+1, parser)
//      }
//   }
//
//   def parseText2(lines : List[String], parsers: List[LogParser]) : Seq[LogEntry] =
//   {
//      val winner = parsers.collectFirst({case x if x.parse(lines.head, 0) != None => x}).get
//   }

   def parseText(text : List[String], parsers: List[LogParser]) : Seq[LogEntry] =
   {
      val any = LogParser.any
      val entries = new ListBuffer[LogEntry]
      var lastWinner : LogParser = any
      var correntIndex = 0

      var currentEntry = new LogEntry()
      for ( line <- text)
      {
         val winner = parsers.collectFirst({case x if x.parse(line, 0) != None => x}).get

         val s: LogLine = if (winner == any)
         {
            if(lastWinner == any)
            {
               currentEntry = new LogEntry
               entries += currentEntry
               any.parse(line, 0).get
            }
            else {
               correntIndex = correntIndex + 1
               lastWinner.parse(line, correntIndex) match {
                  case Some(lup) => lup
                  case _ =>
                     lastWinner = any
                     currentEntry = new LogEntry
                     entries += currentEntry
                     any.parse(line, 0).get
               }
            }
         }
         else
         {
            correntIndex = 0
            lastWinner = winner
            currentEntry = new LogEntry()
            entries += currentEntry
            winner.parse(line, correntIndex).get
         }
         currentEntry.logLines += s
      }
      entries
   }
}
