package zaif4s.dsl

import java.nio.ByteBuffer

import cats.effect.IO
import fs2.Stream
import spray.json.JsonFormat
import zaif4s.dsl.HttpADT.Response

object HttpADT {
  type Bytes = String
  type Response[A] = Either[HttpError, A]
  type ByteStream = Stream[IO, ByteBuffer]
}

sealed trait HttpADT[A]

private[dsl] case class Get[A](
  query: HttpQuery,
  format: JsonFormat[A]
) extends HttpADT[Response[A]]

private[dsl] case class Pure[A](a: A) extends HttpADT[A]
