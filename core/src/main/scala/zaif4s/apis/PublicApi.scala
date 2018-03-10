package zaif4s.apis

import zaif4s.datas._
import zaif4s.dsl.ApiDsl.ApiPrg
import zaif4s.dsl.HttpADT.Response
import zaif4s.dsl.HttpQuery
import zaif4s.formatters.SprayJsonFormats._

class PublicApi extends Api {

  override def baseUrl: String = super.baseUrl + "/api/1"
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

  // http://techbureau-api-document.readthedocs.io/ja/latest/public/2_individual/5_trades.html
  def trades(currencyPair: CurrencyPair): ApiPrg[Response[Seq[Trade]]] =
    get[Seq[Trade]](
      HttpQuery(
        path = s"/trades/$currencyPair",
        credentials = credentials,
        baseUrl = baseUrl
      )
    )

  // http://techbureau-api-document.readthedocs.io/ja/latest/public/2_individual/1_currencies.html
  def currencies(): ApiPrg[Response[Seq[CurrencyInfo]]] =
    get[Seq[CurrencyInfo]](
      HttpQuery(
        path = "/currencies/all",
        credentials = credentials,
        baseUrl = baseUrl
      )
    )

  def currencies(currency: Currency): ApiPrg[Response[Seq[CurrencyInfo]]] =
    get[Seq[CurrencyInfo]](
      HttpQuery(
        path = s"/currencies/$currency",
        credentials = credentials,
        baseUrl = baseUrl
      )
    )

  // http://techbureau-api-document.readthedocs.io/ja/latest/public/2_individual/6_depth.html
  def depth(currencyPair: CurrencyPair): ApiPrg[Response[Depth]] =
    get[Depth](
      HttpQuery(
        path = s"/depth/$currencyPair",
        credentials = credentials,
        baseUrl = baseUrl
      )
    )

  // http://techbureau-api-document.readthedocs.io/ja/latest/public/2_individual/2_currency_pairs.html
  def currencyPairs(): ApiPrg[Response[Seq[CurrencyPairInfo]]] =
    get[Seq[CurrencyPairInfo]](
      HttpQuery(
        path = s"/currency_pairs/all",
        credentials = credentials,
        baseUrl = baseUrl
      )
    )

  def currencyPairs(currencyPair: CurrencyPair): ApiPrg[Response[Seq[CurrencyPairInfo]]] =
    get[Seq[CurrencyPairInfo]](
      HttpQuery(
        path = s"/currency_pairs/$currencyPair",
        credentials = credentials,
        baseUrl = baseUrl
      )
    )
}

object PublicApi extends ApiContent[PublicApi] {
  override def apply(credentials: Credentials): PublicApi =
    new PublicApi
}

