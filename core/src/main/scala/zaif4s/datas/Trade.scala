package zaif4s.datas

case class Trade(
  date: Long,
  price: Float,
  amount: Float,
  tid: Long,
  currencyPair: CurrencyPair,
  tradeType: TradeType
)

sealed trait TradeType {
  def name: String
}

case object BID extends TradeType {
  override def name: String = "bid"
  override def toString: String = name
}

case object ASK extends TradeType {
  override def name: String = "ask"
  override def toString: String = name
}

object TradeType {
  def apply(name: String): TradeType = name match {
    case "bid" => BID
    case "ask" => ASK
    case _ => throw new RuntimeException(s"Invalid TradeType. Input: $name")
  }
}
