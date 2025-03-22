package utils

import java.io.IOException
import java.nio.file.{AccessDeniedException, Files, NoSuchFileException, Paths}
import scala.jdk.CollectionConverters._

class FileReadService {
  def readFromFile(filePath: String): Seq[Int] = {
    val path = Paths.get(filePath)
    if (!Files.exists(path)) throw new FileException("File does not exist.")
    if (!Files.isReadable(path)) throw new FileException("Permission denied: Cannot read file.")
    if (Files.size(path) == 0) throw new FileException("File is already empty.")
    try {
      val content = Files.readAllLines(path).asScala
      content.mkString(",")
        .split(",")
        .map(_.trim)
        .filter(_.nonEmpty)
        .map(_.toInt)
        .toSeq
    } catch {
      case _: NumberFormatException => throw new FileException("Invalid file content: Not all values are numbers.")
      case e: NoSuchFileException => throw new FileException("File not found.")
      case e: IOException => throw new FileException(s"Error reading file: ${e.getMessage}")
      case e: SecurityException => throw new FileException(s"Permission denied: ${e.getMessage}")
      case e: AccessDeniedException => throw new FileException(s"Access denied: ${e.getMessage}")
      case e: UnsupportedOperationException => throw new FileException(s"Unsupported operation: ${e.getMessage}")
      case e: Exception => throw new FileException(s"Unexpected error while reading file: ${e.getMessage}")
    }
  }

}
