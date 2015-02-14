package reactive.api

import reactive.find.FindService
import reactive.hide.HideService
import akka.actor.{ ActorSystem, Props }
import akka.event.Logging.InfoLevel
import spray.http.HttpRequest
import spray.http.StatusCodes.{ MovedPermanently, NotFound }
import spray.routing.{Directives, RouteConcatenation}
import spray.routing.directives.LogEntry

trait AbstractSystem {
  implicit def system: ActorSystem
}

trait ReactiveApi extends RouteConcatenation with StaticRoute with AbstractSystem {
  this: MainActors =>

  val rootService = system.actorOf(Props(classOf[RootService], routes))

  lazy val routes = logRequest(showReq _) {
    new FindService(null).route ~
    new HideService(null).route ~
    staticRoute
  }
  private def showReq(req : HttpRequest) = LogEntry(req.uri, InfoLevel)
}

trait StaticRoute extends Directives {
  this: AbstractSystem =>

  lazy val staticRoute =
    path("favicon.ico") {
      getFromResource("favicon.ico")
    } ~
    pathPrefix("markers") {
      getFromResourceDirectory("markers/")
    } ~
    pathPrefix("css") {
      getFromResourceDirectory("css/")
    } ~
      pathPrefix("hide.js") {
         getFromResource("hide/hide.js")
      } ~
      pathPrefix("find.js") {
         getFromResource("find/find.js")
      } ~
    pathEndOrSingleSlash {
      getFromResource("index.html")
    } ~ complete(NotFound)
}
