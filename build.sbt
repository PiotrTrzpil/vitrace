name := "Vitrace"

version := "0.1"

scalaVersion := "2.10.0"


lazy val rex = project in file("rex-master")

lazy val core = project in file("vitrace-core") dependsOn rex