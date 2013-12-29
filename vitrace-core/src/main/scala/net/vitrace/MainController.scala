package vitrace

import org.slf4j.{LoggerFactory, Logger}


object MainController
{
   private val DefaultPopulationSize = 40;
   private val DefaultFoodSourceCount = 60;
   private val DefaultUpdateInterval = 33d;
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


   private val updateInterval = MainController.DefaultUpdateInterval;
   private var previousTime = System.currentTimeMillis.toDouble;
   private var restTime = MainController.DefaultUpdateInterval;


}
