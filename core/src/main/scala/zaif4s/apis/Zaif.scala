package zaif4s.apis

import zaif4s.datas.Credentials

class Zaif(override val credentials: Credentials) extends Api {

  lazy val publicApi = PublicApi
}

object Zaif extends ApiContent[Zaif] {
  override def apply(credentials: Credentials): Zaif =
    new Zaif(credentials)
}