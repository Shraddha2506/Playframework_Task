package exceptions

import models.FailureResponse
import play.api.libs.json.Json
import play.api.mvc.Result
import play.api.mvc.Results._
import utils.FileException

object ErrorHandler {

  /** Handles FileException errors and returns appropriate HTTP responses */
  def handleFileException(e: FileException): Result = e.getMessage match {
    case "File does not exist." => NotFound (Json.toJson (FailureResponse (e.getMessage) ) )
    case msg if msg.startsWith ("Invalid file content") => BadRequest (Json.toJson (FailureResponse (msg) ) )
    case "Memory full: Cannot write more data to file." => InsufficientStorage (Json.toJson (FailureResponse (e.getMessage) ) )
    case "File is already empty." => Ok (Json.toJson (FailureResponse (e.getMessage) ) )
    //  Handle permission & access-related errors
    case msg if msg.startsWith("Permission denied") => Forbidden(Json.toJson(FailureResponse(msg))) // 403
    case msg if msg.startsWith("Access denied") => Forbidden(Json.toJson(FailureResponse(msg))) // 403
    //  Handle file read/write errors separately
    case msg if msg.startsWith("File write error") => InternalServerError(Json.toJson(FailureResponse(msg))) // 500
    case msg if msg.startsWith("Error reading file") => InternalServerError(Json.toJson(FailureResponse(msg))) // 500
    case _ => InternalServerError (Json.toJson (FailureResponse (e.getMessage) ) )
  }

}

