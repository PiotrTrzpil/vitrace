package pt

object LogLevel extends Enumeration
{
   type LogLevel = Value
   val Trace = Value("Trace")
   val Debug = Value("Debug")
   val Info = Value("Info")
   val Warning = Value("Warning")
   val Error = Value("Error")

}
