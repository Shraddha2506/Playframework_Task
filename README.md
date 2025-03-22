## Play Framework Application

This project implements an API that enables the generation, storage, retrieval, and deletion of integer sequences in a CSV format.

## Key Functionalities
- **Generate & Store:** Creates a sequence of integers within a specified range and saves them to a file.
- **Retrieve Data:** Fetches the stored sequence of numbers from the file.
- **Delete File:** Removes the stored file when no longer needed.
- **Robust Error Handling:** Manages edge cases gracefully, ensuring appropriate responses for invalid inputs, file access issues, and other potential failures.
## Project Structure
### app  
- **controllers** - Handles HTTP requests and interacts with services  
  - `FileController` - Manages file-related API requests  
  - `HomeController` - Default controller (optional)  
- **exceptions** - Handles custom error management  
  - `ErrorHandler` - Centralized error handling  
- **models** - Defines case classes for request/response handling  
  - `FileModels` - Contains model definitions  
- **services** - Implements business logic, including file operations  
  - `FileService` - Coordinates file operations using utils  
- **utils** - Contains helper functions and utilities  
  - `FileException` - Custom exception class for file errors  
  - `FileReadService` - Reads data from file  
  - `FileWriteService` - Writes data to file  
  - `FileClearService` - Clears file content  
- **validators** - Performs input validation to ensure correctness  
  - `RequestValidator` - Ensures inputs are valid before processing  

### conf  
- **application.conf** - Stores application settings  
- **routes** - Defines API routes, linking HTTP endpoints to controllers
  
## Input Handling & Validation
This project ensures robust validation for all inputs by implementing:

**1. JSON Structure Validation**

- Ensures that requests contain valid JSON.
- Returns an error if JSON is malformed.

**2. Business Rule Validation**

- Only allows positive numbers for start and end values.
- Ensures start is less than or equal to end.
- Rejects input if the number range exceeds configured limits.

**3. File Storage Validation**

- Checks if the file exists before reading or writing.
- Ensures that file size does not exceed allowed limits.
- Prevents writing to a full storage by returning a "Memory Full" error instead of crashing.

## Error Handling  

This application includes structured error handling to manage various file-related issues.  

### 1. Input Validation Errors  
- **Invalid JSON input** - Returns **400 Bad Request** with an appropriate message.  
- **Negative numbers in request** - Returns **400 Bad Request**.  
- **Start greater than End** - Returns **400 Bad Request**.  
- **Exceeding max numbers per request** - Returns **400 Bad Request**.  

### 2. File Handling Errors  
- **File does not exist** - Returns **404 Not Found**.  
- **Invalid file content** - Returns **400 Bad Request**.  
- **File is already empty** - Returns **200 OK** with an appropriate message.  
- **Memory full (cannot write more data to file)** - Returns **507 Insufficient Storage**.  
- **Permission denied / Access denied** - Returns **403 Forbidden**.  
- **File write error** - Returns **500 Internal Server Error**.  
- **Error reading file** - Returns **500 Internal Server Error**.  

## Error Handling

- **Returns proper HTTP error responses** for invalid inputs.  
- **Handles file-related errors gracefully**, including storage limits.  
- **Uses structured logging** for debugging and monitoring.  

## Running the Application
#### Navigate to the project directory
cd Playframework_Task

#### Start the application using SBT
sbt run

#### The application will be available at:
http://localhost:9000
