package com.srotip.productservice.exception;

import com.srotip.productservice.dto.ApiResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // ================= PRODUCT NOT FOUND =================
    @ExceptionHandler(ProductNotFoundException.class)
    public ApiResponse<?> handleProductNotFound(ProductNotFoundException ex) {
        return ApiResponse.error(ex.getMessage());
    }

    // ================= VALIDATION ERROR HANDLER =================
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidationErrors(MethodArgumentNotValidException ex) {

        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );

        return ApiResponse.error("Validation failed: " + errors);
    }

    // ================= GENERIC EXCEPTION =================
    @ExceptionHandler(Exception.class)
    public ApiResponse<?> handleGeneric(Exception ex) {
        return ApiResponse.error("Something went wrong: " + ex.getMessage());
    }
}