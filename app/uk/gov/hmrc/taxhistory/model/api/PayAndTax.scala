/*
 * Copyright 2020 HM Revenue & Customs
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

package uk.gov.hmrc.taxhistory.model.api

import java.util.UUID

import org.joda.time.LocalDate
import play.api.libs.json.Json

case class PayAndTax(payAndTaxId: UUID = UUID.randomUUID(),
                     taxablePayTotal: Option[BigDecimal] = None,
                     taxablePayTotalIncludingEYU: Option[BigDecimal] = None,
                     taxTotal: Option[BigDecimal] = None,
                     taxTotalIncludingEYU: Option[BigDecimal] = None,
                     studentLoan: Option[BigDecimal] = None,
                     studentLoanIncludingEYU: Option[BigDecimal] = None,
                     paymentDate: Option[LocalDate] = None,
                     earlierYearUpdates: List[EarlierYearUpdate])

object PayAndTax {
  implicit val formats = Json.format[PayAndTax]
}

