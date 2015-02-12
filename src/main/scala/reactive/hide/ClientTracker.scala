package reactive.hide

import akka.actor.{ActorRef, Actor}
import akka.stream.scaladsl.{Sink, Source}
import spray.can.websocket.frame.{TextFrame, Frame}
import akka.stream.actor._
import reactive.find.AggregateClient.SendToOne
import reactive.find.AggregateClient.SendToOne
import org.joda.time.DateTime
import reactive.find.AggregateClient.SendToOne


case class OlderThan(dateTime:DateTime)

class CommandHandler(updater:ActorRef) extends ActorSubscriber {
   def receive = {

      case ActorSubscriberMessage.OnNext(element) =>
         updater ! OlderThan(DateTime.now)

    //  case _ =>
   }

   protected def requestStrategy = OneByOneRequestStrategy
}
class OnDemandUpdate extends ActorPublisher[Frame] {
   def receive = {

      case OlderThan(dateTime) =>
         (1 to 10).map { el => self ! SendToOne("log line "+el)}


      case SendToOne(message) =>
         if (totalDemand > 0) {
            onNext(TextFrame(message))
         }
      case ActorPublisherMessage.Request(_) =>
   }
}

class ClientTracker(source:Source[Frame], sink:Sink[Frame]) extends Actor {

//   source.runForeach { f =>
//
//   }


   def receive = {
case _ =>
   }
}
