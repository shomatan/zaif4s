name := "zaif4s"

lazy val commonSettings = Seq(
  organization := "me.shoma",
  version := "0.1.0",
  scalaVersion := "2.12.4",
  publishTo := {
    val repo = "https://maven.shoma.me/"
    if (version.value.trim.endsWith("SNAPSHOT"))
      Some("shoma.me snapshots" at repo + "snapshots")
    else
      Some("shoma.me releases"  at repo + "releases")
  },
  credentials += Credentials(Path.userHome / ".ivy2" / ".credentials")
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
  .settings(
    publishArtifact := false,
    publish := {},
    publishLocal := {}
  )
  .aggregate(core, akka)

publishMavenStyle := true
publishArtifact in Test := false

pomIncludeRepository := { _ => false }
