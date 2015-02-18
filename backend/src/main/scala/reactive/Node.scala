package reactive

case class LogNode(text:String, level:String)

object LogLevels {
   val Debug = "Debug"
   val Error = "Error"
   val Info = "Info"
   val Warn = "Warn"
}
