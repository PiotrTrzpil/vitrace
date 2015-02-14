package reactive.hide

import akka.actor.{ActorRef, Actor}
import akka.stream.scaladsl.{Sink, Source}
import spray.can.websocket.frame.{TextFrame, Frame}
import akka.stream.actor._
import org.joda.time.DateTime
import scala.concurrent.duration._
case class OlderThan(dateTime:DateTime)
case class Send(message:String)

class CommandHandler(updater:ActorRef) extends ActorSubscriber {
   def receive = {
      case ActorSubscriberMessage.OnNext(element) =>
         updater ! OlderThan(DateTime.now)
   }

   protected def requestStrategy = OneByOneRequestStrategy
}

class LiveUpdate extends ActorPublisher[Frame] {
   import context.dispatcher
   val scheduled = context.system.scheduler.schedule(2 seconds, 2 seconds, self, Send("newest log line"))

   def receive = {
      case Send(message) =>
         if (totalDemand > 0) {
            onNext(TextFrame(message))
         }
   }
}

class OnDemandUpdate extends ActorPublisher[Frame] {
   def receive = {

      case OlderThan(dateTime) =>
         (1 to 10).map { el => self ! Send("log line "+el)}

      case Send(message) =>
         if (totalDemand > 0) {
            onNext(TextFrame(message))
         }
      case ActorPublisherMessage.Request(_) =>
   }
}

