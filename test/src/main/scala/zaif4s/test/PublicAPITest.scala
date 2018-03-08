package zaif4s.test

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
    depth <- publicApi.depth(BTC_JPY).orFail
  } yield (lastPrice, depth)

  prg.foldMap(httpInterpreter).onComplete {
    case Success(data) =>
      println(data)
      Http().shutdownAllConnectionPools() andThen { case _ => system.terminate() }
    case Failure(ex) => ex.printStackTrace()
  }


}
