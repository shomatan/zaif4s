package zaif4s.datas

case class Depth(asks: Seq[DepthInfo], bids: Seq[DepthInfo])

case class DepthInfo(price: Float, amount: Float)
