## Play Framework Application

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
- **JSON Validation**: Ensures valid JSON structure; rejects malformed requests.  
- **Business Rules**: Allows only positive numbers, enforces `start ≤ end`, and limits request size.  
- **File Storage**: Checks file existence, enforces size limits, and prevents crashes from full storage.  
- **Error Handling**:  
  - `400 Bad Request`: Invalid JSON, negative numbers, `start > end`, exceeding limits, empty file.  
  - `404 Not Found`: File does not exist.  
  - `500 Internal Server Error`: Storage full.  
