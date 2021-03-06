package reactive

import _root_.streamwebsocket.WebSocketMessage
import _root_.streamwebsocket.WebSocketServer
import reactive.api.{MainActors, ReactiveApi}
import akka.actor.{Props, ActorSystem}
import akka.io.IO
import spray.can.Http
import akka.stream.scaladsl._
import spray.can.websocket.frame.{TextFrame, Frame}
import akka.pattern._
import reactive.hide.{LiveUpdate, CommandHandler, OnDemandUpdate}
import akka.stream.actor.{ActorSubscriber, ActorPublisher}
import akka.stream.ActorFlowMaterializer
import akka.util.Timeout
import scala.concurrent.duration._
import spray.can.server.UHttp
import streamwebsocket.WebSocketMessage.Bound
import reactive.find.LiveLogStreamer
import rx.{Observable, RxReactiveStreams}

object ReactiveSystem extends App with MainActors with ReactiveApi {
  implicit lazy val system = ActorSystem("reactive-system")
  implicit val timeout = Timeout(3.seconds)
  implicit val mat = ActorFlowMaterializer()
   import system.dispatcher
   import upickle._
   val liveUpdate = system.actorOf(Props(classOf[LiveUpdate]))

   val liveFile = new LiveLogStreamer(args(0))

   val server = system.actorOf(WebSocketServer.props(), "websocket-server")
   (server ? WebSocketMessage.Bind("localhost", 8080)).map {
      case Bound(addr, connections) =>
         Source(connections).runForeach {
            case WebSocketMessage.Connection(inbound, outbound) =>
               val onDemand = system.actorOf(Props(classOf[OnDemandUpdate]))
               val commandHandler = system.actorOf(Props(classOf[CommandHandler], onDemand))
               FlowGraph  { implicit b =>
                  import FlowGraphImplicits._
                  val merge = Merge[Frame]
                  Source(ActorPublisher(onDemand)) ~> merge

                  Source(RxReactiveStreams.toPublisher[LogNode](liveFile.threadsafeEventBus.asJavaObservable.asInstanceOf[Observable[LogNode]]))
                     .map(e => TextFrame(write(e))) ~> merge ~> Sink(outbound)
               }.run()
               Source(inbound).runWith(Sink(ActorSubscriber[Frame](commandHandler)))
         }
   }

  sys.addShutdownHook({system.shutdown()})
  IO(UHttp) ! Http.Bind(rootService, Configuration.host, port = Configuration.portHttp)
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
