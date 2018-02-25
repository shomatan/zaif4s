package zaif4s.dsl

sealed trait HttpError
//case class RequestError() extends HttpError
case class InvalidResponse(msg: String) extends HttpError
case object ServerDown extends HttpError