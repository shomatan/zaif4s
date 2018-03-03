package zaif4s.formatters

import spray.json._
import zaif4s.datas.{CurrencyPair, _}
import zaif4s.dsl.RequestError

object SprayJsonFormats extends DefaultJsonProtocol {

  implicit object LastPriceFormat extends RootJsonFormat[LastPrice] {
    override def write(obj: LastPrice): JsValue = JsObject(
      "last_price" -> JsNumber(obj.value)
    )
    override def read(json: JsValue): LastPrice =
      json.asJsObject.getFields("last_price") match {
        case Seq(JsNumber(n)) => LastPrice(n.toFloat)
        case other => deserializationError("Cannot deserialize LastPrice: invalid input. Raw input: " + other)
      }
  }

  implicit object CurrencyPairFormat extends RootJsonFormat[CurrencyPair] {
    override def write(obj: CurrencyPair): JsValue = JsObject(
      "currency_pair" -> JsString(obj.toString)
    )
    override def read(json: JsValue): CurrencyPair =
      json.asJsObject.getFields("currency_pair") match {
        case Seq(JsString(s)) => CurrencyPair(s)
        case other => deserializationError("Cannot deserialize TradeType: invalid input. Raw input: " + other)
      }
  }

  implicit object TradeTypeFormat extends RootJsonFormat[TradeType] {
    override def write(obj: TradeType): JsValue = JsObject(
      "trade_type" -> JsString(obj.name)
    )
    override def read(json: JsValue): TradeType =
      json.asJsObject.getFields("trade_type") match {
        case Seq(JsString(s)) => TradeType(s)
        case other => deserializationError("Cannot deserialize TradeType: invalid input. Raw input: " + other)
      }
  }

  class TradeFormat extends RootJsonFormat[Trade] {
    override def write(obj: Trade): JsValue = JsObject(
      "date"          -> JsNumber(obj.date),
      "price"         -> JsNumber(obj.price),
      "amount"        -> JsNumber(obj.amount),
      "tid"           -> JsNumber(obj.tid),
      "currency_pair" -> JsString(obj.currencyPair.toString),
      "trade_type"    -> JsString(obj.tradeType.name)
    )
    override def read(json: JsValue): Trade =
      json.asJsObject.getFields("date", "price", "amount", "tid", "currency_pair", "trade_type") match {
        case Seq(JsNumber(date), JsNumber(price), JsNumber(amount), JsNumber(tid), currencyPair, tradeType) =>
          Trade(
            date = date.toLong,
            price = price.toFloat,
            amount = amount.toFloat,
            tid = tid.toLong,
            currencyPair = currencyPair.convertTo[CurrencyPair],
            tradeType = tradeType.convertTo[TradeType]
          )
        case other => deserializationError("Cannot deserialize Trade: invalid input. Raw input: " + other)
      }
  }

  implicit val requestErrorFormat: RootJsonFormat[RequestError] = jsonFormat1(RequestError)

  implicit val tickerFormat: RootJsonFormat[Ticker] = jsonFormat7(Ticker)
  implicit val tradeFormat: RootJsonFormat[Trade] = new TradeFormat

}
