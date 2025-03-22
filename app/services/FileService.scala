package services

import javax.inject.Inject
import scala.concurrent.{Future, ExecutionContext}
import play.api.Configuration
import utils.{FileWriteService, FileReadService, FileClearService}

class FileService @Inject()(
                             config: Configuration,
                             fileWriteService: FileWriteService,
                             fileReadService: FileReadService,
                             fileClearService: FileClearService
                           )(implicit ec: ExecutionContext) {

  private val filePath: String = config.get[String]("file.storage.path")
  private val maxFileSize: Long = config.get[Long]("file.maxFileSize")

  /** Asynchronous file write */
  def saveToFile(start: Int, end: Int): Future[Unit] =
    Future { fileWriteService.writeToFile(start, end, filePath, maxFileSize) }

  /** Asynchronous file read */
  def fetchFromFile: Future[Seq[Int]] =
    Future { fileReadService.readFromFile(filePath) }

  /** Asynchronous file delete */
  def deleteFile: Future[Boolean] =
    Future { fileClearService.clearFile(filePath) }
}
