name := "vitrace-core"

version := "0.1"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  "org.codehaus.groovy" % "groovy-all" % "2.2.2",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "com.github.nscala-time" % "nscala-time_2.11" % "1.0.0",
  "org.scalatest" % "scalatest_2.11" % "2.1.4" % "compile",
  "org.scalaz" % "scalaz-core_2.11" % "7.0.6")