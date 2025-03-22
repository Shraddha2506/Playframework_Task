package utils

import java.io.IOException
import java.nio.file.{AccessDeniedException, DirectoryNotEmptyException, Files, Paths, StandardOpenOption}

class FileClearService {
  /** Delete file content */
  def clearFile(filePath: String): Boolean = {
    val path = Paths.get(filePath)
    if (!Files.exists(path)) throw new FileException("File does not exist.")
    if (Files.size(path) == 0) throw new FileException("File is already empty.")
    try {
      Files.newBufferedWriter(path, StandardOpenOption.TRUNCATE_EXISTING).close()
      true
    } catch {
      case e: IOException => throw new FileException(s"Error clearing file content: ${e.getMessage}")
      case e: SecurityException => throw new FileException(s"Permission denied: ${e.getMessage}")
      case e: AccessDeniedException => throw new FileException(s"Access denied: ${e.getMessage}")
      case e: DirectoryNotEmptyException => throw new FileException(s"Directory is not empty: ${e.getMessage}")
      case e: UnsupportedOperationException => throw new FileException(s"Unsupported operation: ${e.getMessage}")
      case e: Exception => throw new FileException(s"Unexpected error while clearing file: ${e.getMessage}")
    }
  }
}
