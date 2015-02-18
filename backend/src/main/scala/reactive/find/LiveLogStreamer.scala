package reactive.find

import rx.lang.scala._
import scala.io.{BufferedSource, Codec, Source}
import rx.lang.scala.schedulers.IOScheduler
import rx.lang.scala.subjects._
import java.util.concurrent.Executor
import spray.http.CacheDirectives.public
import org.apache.commons.io.input.{TailerListenerAdapter, Tailer, TailerListener}
import java.io.File
import scala.concurrent.Future
import org.slf4j.{LoggerFactory, Logger}
import reactive.LogNode
import reactive.LogLevels._

class LiveLogStreamer(path:String) {

   val logger = LoggerFactory.getLogger(this.getClass)
   import scala.concurrent.ExecutionContext.Implicits.global

   val eventBus = Subject[LogNode]()
   val threadsafeEventBus = SerializedSubject[LogNode](eventBus)

   val listener = new TailerListenerAdapter {
      override def handle(line:String) = {
         println("Read line from file: "+line)
         threadsafeEventBus.onNext(LogNode(line, Info))
      }
      override def handle(ex:Exception) = {
         println("File tailing error: "+ex)
         logger.error("File tailing error", ex)
      }
   }

   val tailer = new Tailer(new File(path), listener, 300)

   val f = Future {
      scala.concurrent.blocking {
         tailer.run()
      }
   }
}
