package models

import play.api.libs.json._

case class SaveToFileRequest(start: Int, end: Int)
case class SaveToFileResponse(status: String)
case class FetchFromFileResponse(res: Seq[Int])
case class DeleteFromFileResponse(success: Boolean)
case class FailureResponse(status: String)

object FileModels {
  implicit val saveToFileRequestFormat: Format[SaveToFileRequest] = Json.format[SaveToFileRequest]
  implicit val saveToFileResponseFormat: Format[SaveToFileResponse] = Json.format[SaveToFileResponse]
  implicit val fetchFromFileResponseFormat: Format[FetchFromFileResponse] = Json.format[FetchFromFileResponse]
  implicit val deleteFromFileResponseFormat: Format[DeleteFromFileResponse] = Json.format[DeleteFromFileResponse]
}
object FailureResponse {
  implicit val failureResponseWrites: Writes[FailureResponse] = Json.writes[FailureResponse]
}