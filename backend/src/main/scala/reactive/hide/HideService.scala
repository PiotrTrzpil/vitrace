package reactive.hide

import reactive.Configuration
import reactive.api.ApplicationJsonFormats
import akka.actor.{ ActorRef, ActorSystem }
import spray.http.StatusCodes
import spray.routing.Directives

class HideService(hide : ActorRef)(implicit system : ActorSystem) extends Directives with ApplicationJsonFormats {
  lazy val route =
     pathPrefix("resource" / Segment) {  str =>
        get {
           getFromResource(str)
        }
     } ~
    pathPrefix("hide") {
      val dir = "hide/"
      pathEndOrSingleSlash {
        get {
          getFromResource(dir + "index.html")
        }

      } ~
      path("ws") {
        requestUri { uri =>
          val wsUri = uri.withPort(Configuration.portWs)
          redirect(wsUri, StatusCodes.PermanentRedirect)
        }
      } ~
      getFromResourceDirectory(dir)
    }
}
