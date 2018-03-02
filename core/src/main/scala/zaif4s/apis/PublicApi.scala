package zaif4s.apis

import zaif4s.datas._
import zaif4s.dsl.ApiDsl.ApiPrg
import zaif4s.dsl.HttpADT.Response
import zaif4s.dsl.HttpQuery
import zaif4s.formatters.SprayJsonFormats._

class PublicApi extends Api {

  override def baseUrl: String = "https://api.zaif.jp/api/1"
  override def credentials: Credentials = NoCredential

  import zaif4s.dsl.ApiDsl.HttpOp._

  def lastPrice(currencyPair: CurrencyPair): ApiPrg[Response[LastPrice]] =
    get[LastPrice](
      HttpQuery(
        path = s"/last_price/$currencyPair",
        credentials = credentials,
        baseUrl = baseUrl
      )
    )

  // http://techbureau-api-document.readthedocs.io/ja/latest/public/2_individual/4_ticker.html
  def ticker(currencyPair: CurrencyPair): ApiPrg[Response[Ticker]] =
    get[Ticker](
      HttpQuery(
        path = s"/ticker/$currencyPair",
        credentials = credentials,
        baseUrl = baseUrl
      )
    )
}

object PublicApi extends ApiContent[PublicApi] {
  override def apply(credentials: Credentials): PublicApi =
    new PublicApi
}

