package reactive.find

import reactive.WebSocketMessage.ClientOpen
import reactive.socket.ReactiveServer
import akka.actor.{ActorRef, Actor, ActorLogging}
import scala.collection._
import org.java_websocket.WebSocket
import java.util.UUID

object AggregateClient {

   type ClientId = UUID
  case class Unregister(ws : WebSocket)
  case class SendToAll(message: String)
  case class SendToOne(id: ClientId, message: String)
  case class Subscribe()
  case class NewConnection(id: ClientId)
  case class ClientMessage(id: ClientId, message: String)
}
class AggregateClient extends Actor with ActorLogging {
  import AggregateClient._
  import ReactiveServer._

  val clients = mutable.Map[ClientId, WebSocket]()
  val listeners = mutable.ListBuffer[ActorRef]()

  override def receive = {
//    case Open(ws, hs) =>
//       val uuid =  UUID.randomUUID()
//       clients += uuid -> ws
//       listeners.foreach(_ ! NewConnection(uuid))
//    case Close(ws, code, reason, ext) => self ! Unregister(ws)
//    case Error(ws, ex) => self ! Unregister(ws)
//    case Subscribe() => listeners += sender
//    case Message(ws, msg) =>
//       val id = clients.find{case (_, s) => s == ws}.get._1
//       listeners.foreach(_ ! ClientMessage(id, msg))
//      log.debug("url {} received msg '{}'", ws.getResourceDescriptor, msg)
   case SendToAll(msg) =>
         clients.values.foreach(ws => ws.send(msg))

    case Unregister(ws) =>
       if (null != ws) {
         log.debug("unregister")
         clients -= clients.find{case (_, s) => s == ws}.get._1
       }

  }
}
