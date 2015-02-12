package reactive.hide

import akka.actor._
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
          clientHandler ! SendToOne( line)
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
