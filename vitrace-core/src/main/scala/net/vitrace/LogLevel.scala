package net.vitrace

object LogLevel extends Enumeration
{
   type LogLevel = Value
   val Trace = Value("Trace")
   val Debug = Value("Debug")
   val Info = Value("Info")
   val Warn = Value("Warning")
   val Error = Value("Error")

}
