package pt.parsers

import scala.collection.mutable.ListBuffer

class EntriesBuilder
{
   def and(x:Boolean,y:Boolean) : Boolean = if(x) y else false

   def parseText(text : List[String], parsers: List[LogParser]) : Seq[LogEntry] =
   {
      val any = LogParser.any
      val entries = new ListBuffer[LogEntry]
      var lastWinner : LogParser = any

      var currentEntry = new LogEntry()
      for ( line <- text)
      {
         val winner = parsers.collectFirst({case x if x.parse(line) != None => x}).get

         val s: LogLine = if (winner == any)
         {
            if(lastWinner == any)
            {
               currentEntry = new LogEntry
               entries += currentEntry
               any.parse(line).get
            }
            else lastWinner.parseConsecuting(line) match {
               case Some(lup) => lup
               case _ =>
                  lastWinner = any
                  currentEntry = new LogEntry
                  entries += currentEntry
                  any.parse(line).get
            }
         }
         else
         {
            lastWinner = winner
            currentEntry = new LogEntry()
            entries += currentEntry
            winner.parse(line).get
         }
         currentEntry.logLines += s
      }
      entries
   }
}
