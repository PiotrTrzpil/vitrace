package reactive.hide

import reactive.socket.ReactiveServer
import akka.actor._
import org.java_websocket.WebSocket
import scala.collection._
import reactive.find.AggregateClient.{SendToAll, SendToOne, NewConnection, Subscribe}

object HideActor {
  case class LoadLog(logLines:List[String])
}
class HideActor(clientHandler : ActorRef) extends Actor with ActorLogging {
   var logLines = List[String]()

   import HideActor._


   clientHandler ! Subscribe()

  override def receive = {
    case NewConnection(id) => {
       logLines.foreach{ line =>
          clientHandler ! SendToOne(id, line)
       }
    }
    case LoadLog(lines) => {
       logLines = lines
       logLines.foreach{ line =>
          clientHandler ! SendToAll(line)
       }


    }

  }
}
