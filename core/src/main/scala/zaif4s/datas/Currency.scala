package zaif4s.datas

sealed trait Currency

case object JPY extends Currency {
  override def toString: String = "jpy"
}

case object BTC extends Currency {
  override def toString: String = "btc"
}

case object MONA extends Currency {
  override def toString: String = "mona"
}

case object XEM extends Currency {
  override def toString: String = "xem"
}