package zaif4s.formatters

import spray.json._
import zaif4s.datas._
import zaif4s.dsl.RequestError

object SprayJsonFormats extends DefaultJsonProtocol {

  implicit object LastPriceFormat extends RootJsonFormat[LastPrice] {
    override def read(json: JsValue): LastPrice =
      json.asJsObject.getFields("last_price") match {
        case Seq(JsNumber(n)) => LastPrice(n.toFloat)
        case other => deserializationError("Cannot deserialize LastPrice: invalid input. Raw input: " + other)
      }
    override def write(obj: LastPrice): JsValue = JsObject(
      "last_price" -> JsNumber(obj.value)
    )

  }

  implicit val requestErrorFormat: RootJsonFormat[RequestError] = jsonFormat1(RequestError)

  implicit val tickerJsonFormat: RootJsonFormat[Ticker] = jsonFormat7(Ticker)

}
