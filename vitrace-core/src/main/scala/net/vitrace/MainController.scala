package net.vitrace

import org.slf4j.{LoggerFactory, Logger}
import scala.swing.FileChooser
import scala.swing.FileChooser.Result
import net.vitrace.parsers._
import scala.io.Source


object MainController
{
   private val DefaultPopulationSize = 40
   private val DefaultFoodSourceCount = 60
   private val DefaultUpdateInterval = 33d
}

class MainController(panel: MainPanel)
{

   private val logger: Logger = LoggerFactory.getLogger(MainController.getClass)

   def run()
   {
     // val tokenizer = new Tokenizer
      val reader = new LogfileReader
      val lines: Iterator[String] = reader.read("C:\\PLIKI\\Praca\\examplelog.txt")
     // val iterator: Iterator[LogLine] = lines map tokenizer.tokenize
   }

   private val updateInterval = MainController.DefaultUpdateInterval
   private var previousTime = System.currentTimeMillis.toDouble
   private var restTime = MainController.DefaultUpdateInterval

   def openFile()
   {
      val chooser = new FileChooser()
      val result: Result.Value = chooser.showOpenDialog(panel)
      if(result == FileChooser.Result.Approve)
      {
         val lines: Iterator[String] = Source.fromFile(chooser.selectedFile).getLines()
         val o = new SpringCustomParser
         val l: List[LogParser] = List(o, LogParser.any)
         val builder = new EntriesBuilder
         val logEntries: Seq[LogEntry] = builder.parseText(lines.toList, l)

         for(e <- logEntries)
         {
            panel.createBox(e)
         }
       //  logEntries.
         //println()
      }
   }
}
