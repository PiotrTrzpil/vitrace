name := "Vitrace"

version := "0.1"

scalaVersion := "2.10.0"


resolvers += Resolver.sonatypeRepo("releases")

addCompilerPlugin("org.brianmckenna" %% "wartremover" % "0.10")

scalacOptions += "-P:wartremover:traverser:org.brianmckenna.wartremover.warts.Unsafe"



lazy val rex = project in file("rex-master")

lazy val core = project in file("vitrace-core") dependsOn rex