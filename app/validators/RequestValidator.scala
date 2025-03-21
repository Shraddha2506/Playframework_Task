package validators

import play.api.libs.json._
import play.api.mvc._
import play.api.Configuration
import models._
import models.FileModels._
import java.nio.file.{Files, Paths}
import javax.inject.Inject
import scala.util.Try

class RequestValidator @Inject()(config: Configuration) {

  // Read values from `application.conf`
  private val filePath = Paths.get(config.get[String]("file.storage.path"))
  private val maxNumbersPerRequest: Int = config.get[Int]("file.maxNumbersPerRequest")
  private val maxFileSize: Long = config.get[Long]("file.maxFileSize")

  def validateSaveToFileRequest(request: Request[JsValue]): Either[Result, SaveToFileRequest] = {
    // FIRST: Validate JSON Structure (Reject invalid JSON immediately)
    request.body.validate[SaveToFileRequest] match {
      case JsError(_) =>
        Left(Results.BadRequest(Json.toJson(FailureResponse("Invalid JSON input: Please provide valid numbers."))))

      case JsSuccess(req, _) =>
        //  SECOND: Validate Business Rules (Only if JSON is valid)
        val validation = for {
          _ <- check(req.start >= 0 && req.end > 0, "Invalid input: Only positive numbers are allowed.")
          _ <- check(req.start <= req.end, "Invalid range: start must be <= end.")
          _ <- check((req.end - req.start + 1) <= maxNumbersPerRequest, "Input size exceeds the allowed limit.")
          _ <- check(Files.exists(filePath) && Files.size(filePath) <= maxFileSize,
            "Memory full: Cannot write more data to file.")
        } yield req

        validation.left.map(msg => Results.BadRequest(Json.toJson(FailureResponse(msg))))
    }
  }

  // Utility function to simplify validation checks
  private def check(condition: Boolean, errorMsg: String): Either[String, Unit] =
    if (condition) Right(()) else Left(errorMsg)
}
