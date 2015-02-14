package example

import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom._
import japgolly.scalajs.react._
import org.scalajs.d3._
import example.StateAll.State


object StateAll {
   case class Dimen(width:Int, height:Int)
   case class Node(text:String)
   case class DisplayableNode(actual:Node,
                              offset:Int,
                              height: Int,
                              width: Int)
   case class State(items: List[Node])

   class Backend($: BackendScope[_, State]) {
      def start() = {
         $.modState(s => State(s.items :+ Node("test")))
         $.modState(s => State(s.items :+ Node("test")))
         Console.println("Websocket creating.")
         val ws = new dom.WebSocket("ws://127.0.0.1:8080")
         ws.onmessage = (e: MessageEvent) => $.modState(s => State(s.items :+ Node(e.data.toString)))
         ws.onopen = (x: Event) => Console.println("Websocket opened.")
         ws.onerror = (x: ErrorEvent) => Console.println("some error has   occured " + x.message)
         ws.onclose = (x: CloseEvent) => Console.println("Websocket closed.")
      }
   }

}

object SvgElements {
   import example.StateAll._
   import vdom.svg.prefix_<^._
   val LogList = {

      val LogEntry = ReactComponentB[DisplayableNode]("LogEntry")
        .render(node => {
         <.g(
            ^.y:=node.offset,
            <.rect(
               ^.y:=node.offset,
               ^.fill:="white",
               ^.stroke:="red",
               ^.width:=node.width,
               ^.height:=node.height

            ),
              <.text(^.x:=10,
                 ^.y:=node.offset+30,^.fill:="red", "test")
         )
      }).build

      val nodeHeight = 40
      val nodeSpacing = 10
      val LogList = ReactComponentB[(Dimen,List[Node])]("LogList")
        .render ( pair => {
         val (s, entries) = pair
         val toDisplay = entries.zipWithIndex
           .map { case (node, i) => DisplayableNode(node, i*(nodeHeight+nodeSpacing), nodeHeight, 500)}
         <.g(^.width:=s.width,
            ^.height:=s.height, toDisplay.map(LogEntry(_)))
      })
        .build

      LogList
   }
   val Chart = {
      val Chart = ReactComponentB[Dimen]("Chart")
        .render( (s, children) =>
         <.svg(
            ^.width:=s.width,
            ^.height:=s.height,
            children
         )
        )
        .build

      Chart
   }
}




object ScalaJSExample extends js.JSApp {
   import example.StateAll._
  def main(): Unit = {

      val dimen = Dimen(1300, 700)
     import vdom.prefix_<^._
     val TodoApp = ReactComponentB[Unit]("TodoApp")
       .initialState(State(Nil))
       .backend(new Backend(_))
       .render((_,S,B) =>
        <.div(
           SvgElements.Chart(dimen,
              SvgElements.LogList((dimen,S.items))
           )
        )
       ).componentDidMount(_.backend.start())
       .buildU
     React.render(TodoApp(), document.body)
  }
}
