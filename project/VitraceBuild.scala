import sbt._
import Keys._

//object VitraceBuild extends Build {
//    lazy val root = Project(id = "vitrace",
//                            base = file(".")) aggregate(vitrace_core, rex)
//
//    lazy val vitrace_core = Project(id = "vitrace-core",
//                           base = file("vitrace-core")) dependsOn(rex)
//
//    lazy val rex = Project(id = "rex-master",
//                           base = file("rex-master"))
//
//   libraryDependencies += groupID % artifactID % revision
//}