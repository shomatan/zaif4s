package zaif4s.exceptions

import zaif4s.dsl.HttpError

case class ZaifApiException(error: HttpError) extends RuntimeException {
  override def getMessage: String = s"Request failed with error $error"
}
