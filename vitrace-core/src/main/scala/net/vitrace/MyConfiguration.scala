package net.vitrace

import org.slf4j.{LoggerFactory, Logger}

//@Component
class MyConfiguration {

   private val logger: Logger = LoggerFactory.getLogger(this.getClass)

   logger.trace("sdsss");

   def gdad()
   {
      logger.trace("gdad")
   }

}
