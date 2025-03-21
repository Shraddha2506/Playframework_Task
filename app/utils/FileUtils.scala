package utils

import java.io.{BufferedWriter, FileWriter}
import java.nio.file.{Files, Paths, StandardOpenOption}
import scala.util.{Try, Success, Failure}
import scala.jdk.CollectionConverters._

object FileUtils {

  /** Write numbers to file */
  def writeToFile(start: Int, end: Int,filePath: String): Either[String, String] = {
    Try {
      val writer = new BufferedWriter(new FileWriter(Paths.get(filePath).toFile, true))
      try {
        (start to end).foreach { number =>
          writer.write(number.toString)
          writer.write(",")
        }
        writer.write("\n")
      } finally {
        writer.close()
      }
    } match {
      case Success(_)  => Right("success")
      case Failure(ex) => Left(s"File write error: ${ex.getMessage}")
    }
  }

  /** Read numbers from file */
  def readFromFile(filePath: String): Either[String, Seq[Int]] = {
    val path = Paths.get(filePath)
    if (!Files.exists(path)) {
      Left("File does not exist.")
    } else {
      Try {
        val content = Files.readAllLines(path).asScala
        if (content.isEmpty || content.forall(_.trim.isEmpty)) {
          throw new Exception("File is empty.")
        }

        content.mkString(",")
          .split(",")
          .map(_.trim)
          .filter(_.nonEmpty)
          .map(_.toInt)
          .toSeq
      } match {
        case Success(numbers) => Right(numbers)
        case Failure(_: NumberFormatException) => Left("Invalid file content: Not all values are numbers.")
        case Failure(e) if e.getMessage == "File is empty." => Left("File is empty.")
        case Failure(e) => Left(s"Unexpected error: ${e.getMessage}")
      }
    }
  }

  /** Delete file content */
  def clearFile(filePath: String): Either[String, Boolean] = {
    val path = Paths.get(filePath)
    if (!Files.exists(path)) {
      Left("File does not exist.")
    } else if (Files.size(path) == 0) {
      Right(false)
    } else {
      Try {
        Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING).close()
        Right(true)
      } match {
        case Success(result) => result
        case Failure(_) => Left("Error clearing file content.")
      }
    }
  }
}
