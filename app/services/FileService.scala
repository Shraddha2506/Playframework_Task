package services

import javax.inject.Inject
import scala.concurrent.{Future, ExecutionContext}
import play.api.Configuration
import utils.FileUtils

class FileService @Inject()(config: Configuration)(implicit ec: ExecutionContext) {


  private val filePath: String = config.get[String]("file.storage.path")

  /** Asynchronous file write */
  def saveToFile(start: Int, end: Int): Future[Either[String, String]] =
    Future { FileUtils.writeToFile(start, end ,filePath) }

  /** Asynchronous file read */
  def fetchFromFile: Future[Either[String, Seq[Int]]] =
    Future { FileUtils.readFromFile(filePath) }

  /** Asynchronous file delete */
  def deleteFile: Future[Either[String, Boolean]] =
    Future { FileUtils.clearFile(filePath) }
}
