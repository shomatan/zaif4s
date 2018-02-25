package zaif4s.dsl

import cats.{Monad, ~>}
import spray.json.JsonFormat
import zaif4s.dsl.HttpADT.Response

trait ZaifHttpInterpreter[F[_]] extends (HttpADT ~> F) {

  implicit def monad: Monad[F]

  def pure[A](a: A): F[A]

  def get[A](query: HttpQuery, format: JsonFormat[A]): F[Response[A]]

  override def apply[A](fa: HttpADT[A]): F[A] = fa match {
    case Pure(a) => pure(a)
    case Get(query, format) => get(query, format)
  }
}
