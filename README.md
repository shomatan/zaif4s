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

## APIs
See an [official document](http://techbureau-api-document.readthedocs.io/ja/latest/index.html)(Japanese).

### Supported APIs
- [ ] Public
    - [x] Http
    - [ ] WebSocket
- [ ] Trade
- [ ] Public futures
- [ ] Trade leverage

## Example
```
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import cats.implicits._
import zaif4s.apis.PublicApi
import zaif4s.datas._
import zaif4s.interpreters.AkkaHttpInterpreter

import scala.util.{Failure, Success}

object PublicAPITest extends App {

  import zaif4s.dsl.syntax._

  implicit val system = ActorSystem("test")
  implicit val mat = ActorMaterializer()
  implicit val exc = system.dispatcher

  val httpInterpreter = new AkkaHttpInterpreter
  val publicApi = new PublicApi

  val prg = for {
    lastPrice <- publicApi.lastPrice(MONA_JPY).orFail
    currencyPairInfo <- publicApi.currencyPairs().orFail
  } yield (lastPrice, currencyPairInfo)

  prg.foldMap(httpInterpreter).onComplete {
    case Success(data) =>
      println("======= Last price =======")
      println(data._1)
      println("")
      println("======= All currency pair info =======")
      println(data._2)
      Http().shutdownAllConnectionPools() andThen { case _ => system.terminate() }
    case Failure(ex) => ex.printStackTrace()
  }

}
```

