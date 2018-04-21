package zaif4s.dsl

import spray.json.JsonFormat
import zaif4s.dsl.HttpADT.Response

object HttpADT {
  type Bytes = String
  type Response[A] = Either[HttpError, A]
}

sealed trait HttpADT[A]

private[zaif4s] case class Get[A](
  query: HttpQuery,
  format: JsonFormat[A]
) extends HttpADT[Response[A]]

private[zaif4s] case class Pure[A](a: A) extends HttpADT[A]
