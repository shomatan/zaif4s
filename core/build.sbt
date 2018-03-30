name := "zaif4s-core"

libraryDependencies ++= {
  val catsVersion = "1.1.0"
  val monixVersion = "3.0.0-RC1"
  Seq(
    "io.spray"      %% "spray-json"       % "1.3.4",
    "org.typelevel" %% "cats-core"        % catsVersion,
    "org.typelevel" %% "cats-free"        % catsVersion,
    "io.monix"      %% "monix"            % monixVersion,
    "io.monix"      %% "monix-reactive"   % monixVersion,
    "io.monix"      %% "monix-execution"  % monixVersion,
    "io.monix"      %% "monix-eval"       % monixVersion,
    "org.scalatest" %% "scalatest"        % "3.0.1"       % "test"
  )
}