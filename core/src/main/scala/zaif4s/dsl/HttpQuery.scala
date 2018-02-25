package zaif4s.dsl

import zaif4s.datas.Credentials

case class HttpQuery(
  path: String,
  params: Map[String, String] = Map(),
  credentials: Credentials,
  baseUrl: String
)
