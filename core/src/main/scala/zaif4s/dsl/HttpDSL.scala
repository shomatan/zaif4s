package zaif4s.dsl

import cats.free.Free
import spray.json.JsonFormat
import zaif4s.dsl.HttpADT.Response

object HttpDSL {

  type HttpF[A] = Free[HttpADT, A]

  def pure[A](a: A): HttpF[A] =
    Free.liftF(Pure(a))

  def get[A](query: HttpQuery)(implicit format: JsonFormat[A]): HttpF[Response[A]] =
    Free.liftF[HttpADT, Response[A]](Get(query, format))

}
