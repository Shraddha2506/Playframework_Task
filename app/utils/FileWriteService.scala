package utils

import java.io.{BufferedWriter, FileWriter, IOException}
import java.nio.file.{AccessDeniedException, Files, Paths}
import scala.util.{Failure, Success, Try}

class FileWriteService {


  def writeToFile(start: Int, end: Int, filePath: String,maxFileSize: Long): Unit = {
    val path = Paths.get(filePath)
    // Pre-check if the file is writable
    if (Files.exists(path) && !Files.isWritable(path)) throw new FileException("Permission denied: Cannot write to file.")
    if (Files.exists(path) && Files.size(path) >= maxFileSize) {
      throw new FileException("Memory full: Cannot write more data to file.")
    }
    try {
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
    } catch {
      case e: IOException => throw new FileException(s"File write error: ${e.getMessage}")
      case e: SecurityException => throw new FileException(s"Permission denied: ${e.getMessage}")
      case e: AccessDeniedException => throw new FileException(s"Access denied: ${e.getMessage}")
      case e: UnsupportedOperationException => throw new FileException(s"Unsupported operation: ${e.getMessage}")
      case e: Exception => throw new FileException(s"Unexpected error while writing file: ${e.getMessage}")
    }
  }
}
