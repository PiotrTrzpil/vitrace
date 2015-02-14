package example

import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom._
import japgolly.scalajs.react._, vdom.prefix_<^._

object ScalaJSExample extends js.JSApp {


   def appendPar(targetNode: dom.Node, text: String): Unit = {
      val parNode = document.createElement("p")
      val textNode = document.createTextNode(text)
      parNode.appendChild(textNode)
      targetNode.appendChild(parNode)
   }
  def main(): Unit = {
     val LogEntry = ReactComponentB[String]("LogEntry")
       .render(text => {
        <.li(text)
     })
       .build

     val LogList = ReactComponentB[List[String]]("LogList")
       .render(props => {
          <.ul(props map(LogEntry(_)))
       })
       .build

     case class State(items: List[String])

     class Backend($: BackendScope[_, State]) {

        def start() = {
           Console.println("Websocket creating.")
           val ws = new dom.WebSocket("ws://127.0.0.1:8080")
           ws.onmessage = (e: MessageEvent) => $.modState(s => State(s.items :+ e.data.toString))
           ws.onopen = (x: Event) => Console.println("Websocket opened.")
           ws.onerror = (x: ErrorEvent) => Console.println("some error has   occured " + x.message)
           ws.onclose = (x: CloseEvent) => Console.println("Websocket closed.")
        }
     }

     val TodoApp = ReactComponentB[Unit]("TodoApp")
       .initialState(State(Nil))
       .backend(new Backend(_))
       .render((_,S,B) =>
        <.div(
           LogList(S.items)
        )
       ).componentDidMount(_.backend.start())
       .buildU


     React.render(TodoApp(), document.body)

  }
}
