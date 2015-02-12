package reactive

import _root_.streamwebsocket.WebSocketMessage
import _root_.streamwebsocket.WebSocketMessage.Bound
import _root_.streamwebsocket.WebSocketServer
import reactive.api.{MainActors, ReactiveApi}
import akka.actor.{Props, ActorSystem}
import akka.io.{IO, Tcp}
import java.net.InetSocketAddress
import spray.can.Http
import spray.can.server.UHttp
import akka.stream.scaladsl._
import spray.can.websocket.frame.{Frame, TextFrame}
import akka.pattern._
import reactive.hide.{CommandHandler, OnDemandUpdate, ClientTracker}
import akka.stream.actor.{ActorSubscriber, ActorPublisher}
import akka.stream.ActorFlowMaterializer
import akka.util.Timeout
import scala.concurrent.duration._
object ReactiveSystem extends App with MainActors with ReactiveApi {
  implicit lazy val system = ActorSystem("reactive-system")
  implicit val timeout = Timeout(3.seconds)
  implicit val mat = ActorFlowMaterializer()
   import system.dispatcher
 //  val server = system.actorOf(WebSocketServer.props(), "websocket")

   val server = system.actorOf(WebSocketServer.props(), "websocket-server")
   (server ? WebSocketMessage.Bind("localhost", 8080)).map {
      case Bound(addr, connections) =>
         Source(connections).runForeach {
            case WebSocketMessage.Connection(inbound, outbound) =>
               val sink = Sink(outbound)
               val onDemand = system.actorOf(Props(classOf[OnDemandUpdate]))
               val commandHandler = system.actorOf(Props(classOf[CommandHandler]))
               //system.actorOf(Props(classOf[ClientTracker], Source(inbound), sink))
               FlowGraph { implicit b =>
                  import FlowGraphImplicits._
                  val merge = Merge[Frame]
                  Source(ActorPublisher(onDemand)) ~> merge
                  merge ~> sink
               }.run()

               Source(inbound).runWith(Sink(ActorSubscriber[Frame](commandHandler)))

//               Source(inbound).map { case TextFrame(text) =>
//                  TextFrame(text.utf8String.toUpperCase)
//               }.runWith(Sink(outbound))
         }
   }

  sys.addShutdownHook({system.shutdown()})
  IO(Http) ! Http.Bind(rootService, Configuration.host, port = Configuration.portHttp)
  //IO(UHttp) ! Http.Bind(server, Configuration.host, Configuration.portWs)
}

object Configuration {
  import com.typesafe.config.ConfigFactory

  private val config = ConfigFactory.load
  config.checkValid(ConfigFactory.defaultReference)

  val host = config.getString("easter-eggs.host")
  val portHttp = config.getInt("easter-eggs.ports.http")
  val portTcp  = config.getInt("easter-eggs.ports.tcp")
  val portWs   = config.getInt("easter-eggs.ports.ws")
}
