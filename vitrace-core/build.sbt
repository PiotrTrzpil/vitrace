name := "vitrace-core"

version := "0.1"

scalaVersion := "2.10.0"

libraryDependencies ++= Seq(
  "org.codehaus.groovy" % "groovy-all" % "2.2.2",
  "org.slf4j" % "slf4j-api" % "1.7.5",
  "ch.qos.logback" % "logback-classic" % "1.0.13",
  "org.apache.spark" %% "spark-core" % "0.9.1",
  "com.github.nscala-time" %% "nscala-time" % "1.0.0",
  "org.scalatest" %% "scalatest" % "2.1.4" % "compile",
  "org.scala-lang" %% "scala-pickling" % "0.8.0",
  "org.parboiled" %% "parboiled-scala" % "1.1.6",
  "org.scalaz" %% "scalaz-core" % "7.0.6")

resolvers += Resolver.sonatypeRepo("snapshots")