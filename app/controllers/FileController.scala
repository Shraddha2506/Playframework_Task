package controllers

import exceptions.ErrorHandler
import javax.inject._
import play.api.mvc._
import play.api.libs.json._
import services.FileService
import models._
import models.FileModels._
import validators.RequestValidator
import scala.concurrent.{ExecutionContext, Future}
import utils.FileException

//The saveToFile flow starts with input validation using RequestValidator.
// If valid, the request is passed to FileService, which calls FileUtils.writeToFile to append numbers to the file.
// On success, the controller returns HTTP 200 OK;
// otherwise, exceptions are propagated to ErrorHandler, which maps them to appropriate HTTP responses.

@Singleton
class FileController @Inject()(cc: ControllerComponents, fileService: FileService, requestValidator: RequestValidator)(implicit ec: ExecutionContext)
  extends AbstractController(cc) {

  def saveToFile: Action[JsValue] = Action(parse.json).async { request =>
    requestValidator.validateSaveToFileRequest(request) match {
      case Left(errorResponse) => Future.successful(errorResponse)
      case Right(req) =>
        fileService.saveToFile(req.start, req.end)
          .map(_ => Ok(Json.toJson(SaveToFileResponse("success"))))
          .recover { case e: FileException => ErrorHandler.handleFileException(e) }
    }
  }

  def fetchFromFile: Action[AnyContent] = Action.async {
    fileService.fetchFromFile
      .map(numbers => Ok(Json.toJson(FetchFromFileResponse(numbers))))
      .recover { case e: FileException => ErrorHandler.handleFileException(e) }
  }

  def deleteFile: Action[AnyContent] = Action.async {
    fileService.deleteFile
      .map(_ => Ok(Json.toJson(DeleteFromFileResponse(true))))
      .recover { case e: FileException => ErrorHandler.handleFileException(e) }
  }
}
