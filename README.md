# Zaif4s
**Zaif4s** is a [Zaif](https://zaif.jp) API wrapper written in Scala.

## Installation
Zaif4s supports Scala 2.12.
To get started with SBT, simply add the following to your build.sbt file.

```
resolvers ++= Seq(
  "shoma.me Maven Repository snapshots" at "https://maven.shoma.me/snapshots",
  "shoma.me Maven Repository releases" at "https://maven.shoma.me/releases"
)
  
libraryDependencies ++= Seq(
  "me.shoma" %% "zaif4s-core" % "0.1.0-SNAPSHOT",
  "me.shoma" %% "zaif4s-akka" % "0.1.0-SNAPSHOT"
)
```


