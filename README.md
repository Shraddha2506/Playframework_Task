## Play Framework Application

This project implements an API that enables the generation, storage, retrieval, and deletion of integer sequences in a CSV format.

## Key Functionalities
- **Generate & Store:** Creates a sequence of integers within a specified range and saves them to a file.
- **Retrieve Data:** Fetches the stored sequence of numbers from the file.
- **Delete File:** Removes the stored file when no longer needed.
- **Robust Error Handling:** Manages edge cases gracefully, ensuring appropriate responses for invalid inputs, file access issues, and other potential failures.

## Project Structure

**app**
- controllers - Handles HTTP requests and interacts with services
- models - Defines case classes for request/response handling
- services - Implements business logic, including file operations
- validators - Performs input validation to ensure correctness
- utils - Contains helper functions and utilities
- views - (Optional) Used if the application serves frontend pages

**conf**
- application.conf - Stores application settings
- routes - Defines API routes, linking HTTP endpoints to controllers

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

**4. Error Messages for Invalid Input Cases**

- Invalid JSON input → Returns 400 Bad Request with an appropriate message.
- Negative numbers in request → Returns 400 Bad Request with a message.
- Start greater than End → Returns 400 Bad Request.
- Exceeding max numbers per request → Returns 400 Bad Request.
- File does not exist (on read/delete) → Returns 404 Not Found.
- File content is empty (on read) → Returns 400 Bad Request.
- File storage full → Returns 500 Internal Server Error.

## Running the Application
#### Navigate to the project directory
cd Playframework_Task

#### Start the application using SBT
sbt run

#### The application will be available at:
http://localhost:9000
