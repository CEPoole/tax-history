/*
 * Copyright 2017 HM Revenue & Customs
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.gov.hmrc.taxhistory.connectors.des
import play.api.Play
import uk.gov.hmrc.domain.Nino
import uk.gov.hmrc.play.config.ServicesConfig
import uk.gov.hmrc.play.http.{HeaderCarrier, HttpGet, HttpPost}
import uk.gov.hmrc.tai.connectors.BaseConnector
import uk.gov.hmrc.tai.model.rti._
import uk.gov.hmrc.taxhistory.WSHttp

import scala.concurrent.Future

object RtiConnector extends RtiConnector {

  override val httpGet: HttpGet = WSHttp
  override val httpPost: HttpPost = WSHttp

  lazy val serviceUrl: String = s"${baseUrl("rti-hod")}"
  lazy val authorization: String = "Bearer " + getConfString(s"$services.rti-hod.authorizationToken","local")
  lazy val environment: String = getConfString(s"$services.rti-hod.env","local")
  lazy val originatorId = getConfString(s"$services.rti-hod.originatorId","local")
}

trait RtiConnector extends BaseConnector {

  def serviceUrl: String
  def authorization: String
  def environment: String

  def rtiBasicUrl(nino: Nino) = s"$serviceUrl/rti/individual/payments/nino/${withoutSuffix(nino)}"

  def rtiPathUrl(nino: Nino, path: String) = s"${rtiBasicUrl(nino)}/$path"

  def withoutSuffix(nino: Nino) = {
    val BASIC_NINO_LENGTH = 8
    nino.value.take(BASIC_NINO_LENGTH)
  }

  def createHeader: HeaderCarrier = HeaderCarrier(extraHeaders =
    Seq("Environment" -> environment,
      "Authorization" -> authorization,
      "Gov-Uk-Originator-Id" -> originatorId))

  def getRTI(nino: Nino, taxYear: Int)(implicit hc: HeaderCarrier): Future[Option[RtiData]] = {
    val urlToRead = rtiPathUrl(nino, s"tax-year/${taxYear}") // two digit tax year e.g. 17 or 18 etc
    implicit val hc: HeaderCarrier = createHeader
    getFromRTIWithStatus(urlToRead, withoutSuffix(nino))
  }

}
