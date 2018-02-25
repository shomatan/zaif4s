package zaif4s.dsl

import zaif4s.dsl.ApiDsl.ApiPrg
import zaif4s.dsl.HttpADT.Response
import zaif4s.exceptions.ZaifApiException

object syntax {

  import ApiDsl.HttpOp._

  implicit class ResponseOps[A](response: Response[A]) {
    def orFail: ApiPrg[A] =
      response match {
        case Right(value) => pure(value.asInstanceOf[A])
        case Left(error) => throw ZaifApiException(error)
      }
  }

  implicit class ApiOps[A](apiPrg: ApiPrg[Response[A]]) {
    def orFail: ApiPrg[A] =
      apiPrg.flatMap {
        case Right(value) => pure(value)
        case Left(error) => throw ZaifApiException(error)
      }
  }

  implicit class ApiSeqOps[A](apiPrgs: Seq[ApiPrg[A]]) {
    def sequential: ApiPrg[Seq[A]] =
      apiPrgs.foldLeft(pure(Seq.empty[A])) {
        case (newPrg, prg) =>
          newPrg.flatMap { results =>
            prg.map { result =>
              results :+ result
            }
          }
      }
  }

}
