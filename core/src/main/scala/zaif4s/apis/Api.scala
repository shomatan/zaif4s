package zaif4s.apis

import zaif4s.datas.Credentials

trait Api {
  def baseUrl: String = "https://api.zaif.jp"
  def credentials: Credentials
}

trait ApiContent[A <: Api] {
  def apply(credentials: Credentials): A
}