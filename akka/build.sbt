name := "zaif4s-akka"

libraryDependencies ++= Seq(
  "com.typesafe.akka"     %% "akka-http"            % "10.0.11",
  "com.typesafe.akka"     %% "akka-stream"          % "2.5.9",
  "com.github.zainab-ali" %% "fs2-reactive-streams" % "0.2.6",
  "co.fs2"                %% "fs2-core"             % "0.10.0-M9",
  "co.fs2"                %% "fs2-io"               % "0.10.0-M9",
  "org.scalatest"         %% "scalatest"            % "3.0.1"       % "test"
)