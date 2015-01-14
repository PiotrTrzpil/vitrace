package reactive.api

import reactive.find.AggregateClient
import reactive.hide.HideActor

import akka.actor.Props
import reactive.hide.HideActor.LoadLog
import scala.io.BufferedSource

trait MainActors {
  this: AbstractSystem =>

  lazy val find = system.actorOf(Props[AggregateClient], "find")
  lazy val hide = system.actorOf(Props(classOf[HideActor], find), "hide")



   def read(path:String ) =
   {
      val source: BufferedSource = scala.io.Source.fromFile(path)
      val lines = source.getLines().toList
      source.close()
      lines
   }

   hide ! LoadLog(read("C:\\PLIKI\\Programowanie\\Scala\\vitrace-testy\\catalina-small.txt"))
}
