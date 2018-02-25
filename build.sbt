name := "zaif4s"

lazy val commonSettings = Seq(
  organization := "com.github.shomatan",
  version := "0.1.0-SNAPSHOT",
  scalaVersion := "2.12.4",
  publishTo := Some(
    if (isSnapshot.value)
      Opts.resolver.sonatypeSnapshots
    else
      Opts.resolver.sonatypeStaging
  )
)

lazy val core = (project in file("core"))
  .settings(commonSettings)

lazy val akka = (project in file("akka"))
  .settings(commonSettings)
  .dependsOn(core)

lazy val test = (project in file("test"))
  .settings(commonSettings)
  .dependsOn(core, akka)

lazy val root = (project in file("."))
  .settings(commonSettings: _*)
  .dependsOn(core, akka)

licenses := Seq("APL2" -> url("http://www.apache.org/licenses/LICENSE-2.0.txt"))
homepage := Some(url("https://github.com/shomatan"))

publishMavenStyle := true
publishArtifact in Test := false

scmInfo := Some(
  ScmInfo(
    url("https://github.com/shomatan/zaif4s"),
    "scm:git@github.com:shomatan/zaif4s.git"
  )
)

sonatypeProfileName := "com.github.shomatan"

developers := List(
  Developer(
    id    = "shomatan",
    name  = "Shoma Nishitateno",
    email = "shoma416@gmail.com",
    url   = url("https://shoma.me")
  )
)