package example

import scala.scalajs.js
import org.scalajs.dom
import org.scalajs.dom._
import japgolly.scalajs.react._
import org.scalajs.d3._
import example.StateAll.State
import upickle._

object StateAll {
   case class Dimen(width:Int, height:Int)

   case class DisplayableNode(
                               id:String,
                               actual:LogNode,
                              offset:Int,
                              height: Int,
                              width: Int)
   case class State(items: List[LogNode])

   class Backend($: BackendScope[_, State]) {
      def start() = {
         Console.println("Websocket creating.")
         $.modState(s => State(s.items :+ LogNode("test", "Info")))
         val ws = new dom.WebSocket("ws://127.0.0.1:8080")
         ws.onmessage = (e: MessageEvent) =>
            $.modState(s => State(s.items :+ read[LogNode](e.data.toString)))
         ws.onopen = (x: Event) => Console.println("Websocket opened.")
         ws.onerror = (x: ErrorEvent) => Console.println("some error has   occured " + x.message)
         ws.onclose = (x: CloseEvent) => Console.println("Websocket closed.")
      }
   }

}




object Color {
   def fill(level:String) = level match {
      case "Debug" => "#12106a"
      case "Info" => "#034600"
      case "Warn" => "#5b4b0c"
      case "Error" => "#6b1c1c"
   }
   def stroke(level:String) = level match {
      case "Debug" => "#18487d"
      case "Info" => "#1f5a29"
      case "Warn" => "#746b16"
      case "Error" => "#95262a"
   }
}

object SvgElements {
   import example.StateAll._
   import vdom.svg.prefix_<^._
   val LogList = {

      val LogEntry = ReactComponentB[DisplayableNode]("LogEntry")
        .render(node => {
         <.g(
            ^.id := node.id,
            ^.y:=node.offset,
            <.rect(
               ^.rx:=5,
               ^.ry:=5,
               ^.y:=node.offset,
               ^.fill:=Color.fill(node.actual.level),
               ^.stroke:=Color.stroke(node.actual.level),
            ^.strokeWidth:=1.8,
               ^.width:=node.width,
               ^.height:=node.height
            ),
              <.text(^.x:=10,
                 ^.y:=node.offset+25,^.fill:="white", node.actual.text)
         )
      }).build

      val nodeHeight = 40
      val nodeSpacing = 10
      val LogList = ReactComponentB[(Dimen,List[LogNode])]("LogList")
        .render ( pair => {
         val (s, entries) = pair
         val toDisplay = entries.zipWithIndex
           .map { case (node, i) => DisplayableNode("node-"+i,node, i*(nodeHeight+nodeSpacing), nodeHeight, 1300)}
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
            ^.id := "canvas",
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
