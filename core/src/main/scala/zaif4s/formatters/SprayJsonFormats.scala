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

  class CurrencyPairInfoFormat extends RootJsonFormat[CurrencyPairInfo] {
    override def write(obj: CurrencyPairInfo): JsValue = JsObject(
      "name"            -> JsString(obj.name),
      "title"           -> JsString(obj.title),
      "currency_pair"   -> JsString(obj.currencyPairName),
      "description"     -> JsString(obj.description),
      "is_token"        -> JsBoolean(obj.isToken),
      "event_number"    -> JsNumber(obj.eventNumber),
      "item_unit_min"   -> JsNumber(obj.itemUnitMin),
      "item_unit_step"  -> JsNumber(obj.itemUnitStep),
      "aux_unit_min"    -> JsNumber(obj.auxUnitMin),
      "aux_unit_step"   -> JsNumber(obj.auxUnitStep),
      "seq"             -> JsNumber(obj.seq),
      "aux_japanese"    -> JsString(obj.auxJapanese),
      "item_japanese"   -> JsString(obj.itemJapanese),
      "aux_unit_point"  -> JsNumber(obj.auxUnitPoint)
    )
    override def read(json: JsValue): CurrencyPairInfo =
      json.asJsObject.getFields("name", "title", "currency_pair", "description", "is_token", "event_number",
        "item_unit_min", "item_unit_step", "aux_unit_min", "aux_unit_step", "seq", "aux_japanese",
        "item_japanese", "aux_unit_point") match {
        case Seq(JsString(name), JsString(title), JsString(currencyPair), JsString(description), JsBoolean(isToken),
          JsNumber(eventNumber), JsNumber(itemUnitMin), JsNumber(itemUnitStep), JsNumber(auxUnitMin),
          JsNumber(auxUnitStep), JsNumber(seq), JsString(auxJapanese), JsString(itemJapanese),
          JsNumber(auxUnitPoint)) =>
          CurrencyPairInfo(
            name = name,
            title = title,
            currencyPairName = currencyPair,
            description = description,
            isToken = isToken,
            eventNumber = eventNumber.toInt,
            itemUnitMin = itemUnitMin.toFloat,
            itemUnitStep = itemUnitStep.toFloat,
            auxUnitMin = auxUnitMin.toFloat,
            auxUnitStep = auxUnitStep.toFloat,
            seq = seq.toInt,
            auxJapanese = auxJapanese,
            itemJapanese = itemJapanese,
            auxUnitPoint = auxUnitPoint.toInt
          )
        case other => deserializationError("Cannot deserialize Trade: invalid input. Raw input: " + other)
      }
  }

  implicit object CurrencyInfoFormat extends RootJsonFormat[CurrencyInfo] {
    override def write(obj: CurrencyInfo): JsValue = JsObject(
      "name" -> JsString(obj.name),
      "is_token" -> JsBoolean(obj.isToken)
    )
    override def read(json: JsValue): CurrencyInfo =
      json.asJsObject.getFields("name", "is_token") match {
        case Seq(JsString(name), JsBoolean(isToken)) => CurrencyInfo(name, isToken)
        case other => deserializationError("Cannot deserialize CurrencyInfo: invalid input. Raw input: " + other)
      }
  }

  implicit object DepthInfoFormat extends RootJsonFormat[DepthInfo] {
    override def write(obj: DepthInfo): JsValue = JsArray(JsNumber(obj.price), JsNumber(obj.amount))
    override def read(json: JsValue): DepthInfo = {
      val array = json.convertTo[JsArray]
      if (array.elements.length == 2) {
        DepthInfo(array.elements(0).convertTo[Float], array.elements(1).convertTo[Float])
      } else {
        deserializationError("Cannot deserialize DepthInfo: invalid input. Raw input: " + json.toString)
      }
    }
  }

  implicit object DepthFormat extends RootJsonFormat[Depth] {
    override def write(obj: Depth): JsValue = JsObject(
      "asks" -> obj.asks.map(_.toJson).toJson,
      "bids" -> obj.bids.map(_.toJson).toJson
    )
    override def read(json: JsValue): Depth =
      json.asJsObject.getFields("asks", "bids") match {
        case Seq(JsArray(asks), JsArray(bids)) => Depth(
          asks = asks.map(_.convertTo[DepthInfo]),
          bids = bids.map(_.convertTo[DepthInfo])
        )
        case other => deserializationError("Cannot deserialize Depth: invalid input. Raw input: " + other)
      }
  }

  implicit val requestErrorFormat: RootJsonFormat[RequestError] = jsonFormat1(RequestError)

  implicit val tickerFormat: RootJsonFormat[Ticker] = jsonFormat7(Ticker)
  implicit val tradeFormat: RootJsonFormat[Trade] = new TradeFormat
  implicit val currencyPairInfoFormat: RootJsonFormat[CurrencyPairInfo] = new CurrencyPairInfoFormat

}
