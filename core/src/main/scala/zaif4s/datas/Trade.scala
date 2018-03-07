package zaif4s.datas

case class Trade(
  date: Long,
  price: Float,
  amount: Float,
  tid: Long,
  currencyPair: CurrencyPair,
  tradeType: TradeType
)

sealed abstract class TradeType(val name: String)

case object BID extends TradeType("bid") {
  override def toString: String = name
}

case object ASK extends TradeType("ask") {
  override def toString: String = name
}

object TradeType {
  def apply(name: String): TradeType = name match {
    case BID.name => BID
    case ASK.name => ASK
    case _ => throw new RuntimeException(s"Invalid TradeType. Input: $name")
  }
}
