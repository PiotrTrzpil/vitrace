package reactive.find

import akka.actor.{ActorRef, Actor, ActorLogging}
import scala.collection._
import java.util.UUID
import org.reactivestreams.{Publisher, Subscriber}

object AggregateClient {

   type ClientId = UUID
  case class Register(publisher : Publisher[String], subscriber:Subscriber[String])
  case class Unregister(ws : ActorRef)
  case class SendToAll(message: String)
  case class SendToOne(message: String)
  case class Subscribe()
  case class NewConnection(id: ClientId)
  case class ClientMessage(id: ClientId, message: String)
}
class AggregateClient extends Actor with ActorLogging {
  import AggregateClient._

  val clients = mutable.Map[ClientId, ActorRef]()
  val listeners = mutable.ListBuffer[ActorRef]()

  override def receive = {
    case Register(ref, rt) =>
       val uuid =  UUID.randomUUID()
      // clients += uuid -> ref
       listeners.foreach(_ ! NewConnection(uuid))
//    case Close(ws, code, reason, ext) => self ! Unregister(ws)
//    case Error(ws, ex) => self ! Unregister(ws)
//    case Subscribe() => listeners += sender
//    case Message(ws, msg) =>
//       val id = clients.find{case (_, s) => s == ws}.get._1
//       listeners.foreach(_ ! ClientMessage(id, msg))
//      log.debug("url {} received msg '{}'", ws.getResourceDescriptor, msg)
   case SendToAll(msg) =>
         clients.values.foreach(ref => ref ! msg)
    case SendToOne( msg) =>
      // clients(id) ! msg

    case Unregister(ws) =>
       if (null != ws) {
         log.debug("unregister")
         clients -= clients.find{case (_, s) => s == ws}.get._1
       }

  }
}
