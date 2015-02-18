organization  := "pt"

version       := "0.3"

scalaVersion  := "2.11.5"

resolvers += "Spray" at "http://repo.spray.io"

resolvers ++= Seq("snapshots", "releases").map(Resolver.sonatypeRepo)

val SharedSrcDir = "shared"


lazy val root = project.in(file(".")).
  aggregate(sharedJS, sharedJVM).
  settings(
     publish := {},
     publishLocal := {}
  )

lazy val shared = crossProject.in(file("shared"))
  .settings(
     scalaVersion  := "2.11.5"
  )

lazy val sharedJS = shared.js
lazy val sharedJVM = shared.jvm

lazy val frontend = project.in(file("frontend"))
  .settings(
     scalaVersion  := "2.11.5",
     libraryDependencies ++= Seq(
        "com.github.japgolly.scalajs-react" %%% "core" % "0.8.0",
        "org.spaced.scalajs" %%% "scalajs-d3" % "0.1-SNAPSHOT",
        "com.lihaoyi" %%% "upickle" % "0.2.6"
     ),
     jsDependencies += "org.webjars" % "react" % "0.12.1" / "react-with-addons.js" commonJSName "React",
     jsDependencies += "org.webjars" % "d3js" % "3.5.3" / "d3.js" commonJSName "d3"
  )
  .enablePlugins(ScalaJSPlugin)
.dependsOn(sharedJS)

lazy val backend = project.in(file("backend"))
  .settings(
     resolvers += "bintray/non" at "http://dl.bintray.com/non/maven",
     scalaVersion  := "2.11.5",
     libraryDependencies ++= {
        val akkaV  = "2.3.8"
        val sprayV = "1.3.2"
        Seq(
           "com.lihaoyi" %% "upickle" % "0.2.6",
           "io.spray"            %%  "spray-json"     % "1.3.1" withSources(),
           "io.spray"            %%  "spray-can"      % sprayV  withSources(),
           "io.spray"            %%  "spray-routing"  % sprayV  withSources(),
           "pt" %% "akka-stream-websocket" % "0.1",
           "io.reactivex" % "rxscala_2.11" % "0.23.1",
           "commons-io" % "commons-io" % "2.4",
           "io.reactivex" % "rxjava-reactive-streams" % "0.3.0",
           "com.typesafe.akka"   %%  "akka-actor"     % akkaV   withSources(),
           "com.typesafe.akka"   %%  "akka-actor"     % akkaV   withSources(),
           "com.typesafe.akka"   %%  "akka-testkit"   % akkaV    % "test" withSources(),
           "io.spray"            %%  "spray-testkit"  % sprayV   % "test" withSources(),
           "junit"               %   "junit"          % "4.12"   % "test",
           "org.specs2"          %%  "specs2-core"         % "2.4.15" % "test" withSources(),
           "com.github.nscala-time" %% "nscala-time" % "1.8.0"
        )
     }
  ).dependsOn(sharedJVM)

scalacOptions ++= Seq("-deprecation", "-encoding", "UTF-8", "-feature", "-target:jvm-1.7", "-unchecked",
  "-Ywarn-adapted-args", "-Ywarn-value-discard", "-Xlint", "-Yrangepos")

javacOptions ++= Seq("-Xlint:deprecation", "-Xlint:unchecked", "-source", "1.7", "-target", "1.7", "-g:vars")

doc in Compile <<= target.map(_ / "none")

publishArtifact in (Compile, packageSrc) := false

logBuffered in Test := false

Keys.fork in Test := false

parallelExecution in Test := false

seq(Revolver.settings: _*)

