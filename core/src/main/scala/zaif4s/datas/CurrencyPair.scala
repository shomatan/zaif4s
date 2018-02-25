package zaif4s.datas

sealed trait CurrencyPair {

  def currency: Currency

  def counterCurrency: Currency

  override def toString: String = s"${currency}_$counterCurrency"
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

case object XEMBTC extends CurrencyPair {
  override def currency: Currency = XEM
  override def counterCurrency: Currency = BTC
}

case object MONA_BTC extends CurrencyPair {
  override def currency: Currency = MONA
  override def counterCurrency: Currency = BTC
}