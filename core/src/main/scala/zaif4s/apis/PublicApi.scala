package zaif4s.apis
import zaif4s.datas.{Credentials, NoCredential}

class PublicApi extends Api {
  override def baseUrl: String = "https://api.zaif.jp/api/1"
  override def credentials: Credentials = NoCredential
}

object PublicApi extends ApiContent[PublicApi] {
  override def apply(credentials: Credentials): PublicApi =
    new PublicApi
}

