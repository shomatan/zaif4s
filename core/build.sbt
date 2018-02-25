name := "zaif4s-core"

libraryDependencies ++= {
  val catsVersion = "1.0.1"
  Seq(
    "io.spray"      %% "spray-json"   % "1.3.4",
    "org.typelevel" %% "cats-core"    % catsVersion,
    "org.typelevel" %% "cats-kernel"  % catsVersion,
    "org.typelevel" %% "cats-macros"  % catsVersion,
    "org.typelevel" %% "cats-free"    % catsVersion,
    "co.fs2"        %% "fs2-core"     % "0.10.0-RC1",
    "co.fs2"        %% "fs2-io"       % "0.10.0-RC1",
    "org.scalatest" %% "scalatest"    % "3.0.1"       % "test"
  )
}