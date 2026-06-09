package poly.edu.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice(basePackages = "poly.edu.api")
public class GlobalApiExceptionHandler {
	@ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<String>> handleAllException(Exception ex) {
        ex.printStackTrace(); 
        
        return ResponseEntity.status(500)
                .body(new ApiResponse<>(500, "Lỗi Server: " + ex.getMessage(), null));
    }
}
