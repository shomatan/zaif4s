package zaif4s.datas

case class CurrencyPairInfo(
  name: String,
  title: String,
  currencyPairName: String,
  description: String,
  isToken: Boolean,
  eventNumber: Int,
  itemUnitMin: Float,
  itemUnitStep: Float,
  auxUnitMin: Float,
  auxUnitStep: Float,
  seq: Int,
  auxJapanese: String,
  itemJapanese: String,
  auxUnitPoint: Int
)

sealed trait CurrencyPair {
  def currency: Currency
  def counterCurrency: Currency
  override def toString: String = s"${currency}_$counterCurrency"
}

object CurrencyPair {
  def apply(value: String): CurrencyPair = value match {
    case "btc_jpy"  => BTC_JPY
    case "mona_jpy" => MONA_JPY
    case "xem_jpy"  => XEM_JPY
    case "xem_btc"  => XEM_BTC
    case "mona_btc" => MONA_BTC
    case _ => throw new RuntimeException(s"Invalid CurrencyPair. Input: $value")
  }
}

case object BTC_JPY extends CurrencyPair {
  override def currency: Currency = BTC
  override def counterCurrency: Currency = JPY
}

case object MONA_JPY extends CurrencyPair {
  override def currency: Currency = MONA
  override def counterCurrency: Currency = JPY
}

case object XEM_JPY extends CurrencyPair {
  override def currency: Currency = XEM
  override def counterCurrency: Currency = JPY
}

case object XEM_BTC extends CurrencyPair {
  override def currency: Currency = XEM
  override def counterCurrency: Currency = BTC
}

case object MONA_BTC extends CurrencyPair {
  override def currency: Currency = MONA
  override def counterCurrency: Currency = BTC
}