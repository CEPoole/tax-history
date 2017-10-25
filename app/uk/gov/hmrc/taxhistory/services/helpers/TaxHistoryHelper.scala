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

package uk.gov.hmrc.taxhistory.services.helpers


import scala.util.{Failure, Success, Try}

trait TaxHistoryHelper  {

  def fetchResult[A,B](either:Either[A,B]):Option[B]={
    either match {
      case Left(x) => None
      case Right(x) => Some(x)
    }
  }


  def fetchFilteredList[A](listToFilter:List[A])(f:(A) => Boolean):List[A] = {
    listToFilter.filter(f(_))
  }

  def formatString(a: String):String = {
    Try(a.toInt) match {
      case Success(x) => x.toString
      case Failure(y) => a
    }
  }

}