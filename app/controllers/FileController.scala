package controllers

import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import services.FileService
import models._
import models.FileModels._
import validators.RequestValidator
import scala.concurrent.{ExecutionContext, Future}

@Singleton
class FileController @Inject()(cc: ControllerComponents, fileService: FileService,requestValidator: RequestValidator)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def saveToFile: Action[JsValue] = Action(parse.json).async { request =>
    requestValidator.validateSaveToFileRequest(request) match {
      case Left(errorResponse) => Future.successful(errorResponse)
      case Right(req) =>
        fileService.saveToFile(req.start, req.end).map {
          case Right(_) => Ok(Json.toJson(SaveToFileResponse("success")))
          case Left(_) => InternalServerError(Json.toJson(FailureResponse("failed")))
        }
    }
  }

  def fetchFromFile: Action[AnyContent] = Action.async {
    fileService.fetchFromFile.map {
      case Right(numbers) => Ok(Json.toJson(FetchFromFileResponse(numbers))) // Return fetched numbers
      case Left("File is empty.") => Ok(Json.toJson(FailureResponse("File is empty."))) // 200 OK with error message
      case Left("File does not exist.") => NotFound(Json.toJson(FailureResponse("File does not exist."))) //  Return 404
      case Left("Invalid file content: Not all values are numbers.") =>
        BadRequest(Json.toJson(FailureResponse("Invalid file content: Not all values are numbers."))) // Return 400 Bad Request
      case Left(err) => InternalServerError(Json.toJson(FailureResponse(err))) // Handle other errors
    }
  }

  def deleteFile: Action[AnyContent] = Action.async {
    fileService.deleteFile.map {
      case Right(true)  => Ok(Json.toJson(DeleteFromFileResponse(true))) // Numbers deleted
      case Left("File does not exist.") => NotFound(Json.toJson(FailureResponse("File does not exist.")))
      case Right(false) => UnprocessableEntity(Json.toJson(FailureResponse("File is already empty."))) // File is empty but return 200 OK
      case Left(err)    => InternalServerError(Json.toJson(FailureResponse(err))) // Handle other errors
    }
  }





}
