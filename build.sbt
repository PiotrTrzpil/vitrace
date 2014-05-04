name := "Vitrace"

version := "0.1"

scalaVersion := "2.11.0"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "com.github.nscala-time" % "nscala-time_2.11" % "1.0.0",
  "org.scalaz" % "scalaz-core_2.11" % "7.0.6")

lazy val rex = project in file("rex-master")

lazy val core = project in file("vitrace-core") dependsOn rex