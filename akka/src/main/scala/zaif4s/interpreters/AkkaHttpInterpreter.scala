package zaif4s.interpreters

import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.marshalling.Marshal
import akka.http.scaladsl.model.Uri.Query
import akka.http.scaladsl.model._
import akka.stream.Materializer
import cats.Monad
import cats.implicits._
import spray.json._
import zaif4s.datas.NoCredential
import zaif4s.dsl.HttpADT.{Bytes, Response}
import zaif4s.dsl._

import scala.collection.immutable.Seq
import scala.concurrent.{ExecutionContext, Future}
import scala.concurrent.duration._

class AkkaHttpInterpreter(implicit actorSystem: ActorSystem, mat: Materializer,
                          exc: ExecutionContext) extends ZaifHttpInterpreter[Future] {

  import zaif4s.formatters.SprayJsonFormats._

  implicit val monad = implicitly[Monad[Future]]
  
  private val http = Http()
  private val timeout = 10.seconds
  private val maxRedirCount = 20
  private val reqHeaders: Seq[HttpHeader] = Seq(
    headers.`User-Agent`("zaif4s"),
    headers.`Accept-Charset`(HttpCharsets.`UTF-8`)
  )

  private def createRequest(method: HttpMethod, query: HttpQuery): HttpRequest =
    query.credentials match {
      case NoCredential =>
        HttpRequest(
          method = method,
          uri = Uri(query.baseUrl + query.path).withQuery(Query(query.params))
        )
    }

  private def createRequest[Payload](method: HttpMethod,
                                     query: HttpQuery,
                                     payload: Payload,
                                     format: JsonFormat[Payload]): Future[HttpRequest] = {
    val formData = FormData(
      payload.toJson(format).asJsObject.fields.map {
        case (key, JsString(value)) => key -> value
        case (key, value) => key -> value.toString()
      }
    )

    Marshal(formData).to[RequestEntity].map { entity =>
      createRequest(method, query).withEntity(entity)
    }
  }

  private def doRequest(request: HttpRequest): Future[Response[Bytes]] =
    for {
      response <- http.singleRequest(request)
      data <- response.entity.toStrict(timeout).map(_.data.utf8String)
      result = {
        val status = response.status.intValue()
        if (response.status.isFailure()) {
          if (status >= 400 && status < 500)
            Either.left(data.parseJson.convertTo[RequestError])
          else {
            Either.left(ServerDown)
          }
        } else {
          Either.right(data)
        }
      }
    } yield result

  override def pure[A](a: A): Future[A] = Future.successful(a)

  override def get[A](query: HttpQuery, format: JsonFormat[A]): Future[Response[A]] =
    for {
      serverResponse <- doRequest(createRequest(HttpMethods.GET, query))
      response = serverResponse.map(_.parseJson.convertTo[A](format))
    } yield response

}
