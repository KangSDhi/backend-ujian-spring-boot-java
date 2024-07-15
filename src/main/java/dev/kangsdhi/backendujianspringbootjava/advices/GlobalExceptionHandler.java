package dev.kangsdhi.backendujianspringbootjava.advices;

import dev.kangsdhi.backendujianspringbootjava.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    private static final String JWT_INVALID_FORMAT = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJLYW5nIEFkbWluIiwiaWF0IjoxNzIwNjEzN2OTk5OTJ9.KGykxpia76MOoqp_g7H0Nph4wJDurMiA";

    private static final String JWT_EXPIRED = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJLYW5nIEFkbWluIiwiaWF0IjoxNzIwNjEzNTkyLCJleHAiOjE3MjA2OTk5OTJ9.KGykxpiaoVTDY9oql-776MOoqp_g7H0Nph4wJDurMiA";

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ErrorResponse<String>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>();
        errorResponse.setHttpCode(HttpStatus.NOT_FOUND.value());
        errorResponse.setErrors(e.getMessage());
        logger.warn(e.getMessage());
        logger.warn(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>();
        errorResponse.setHttpCode(HttpStatus.BAD_REQUEST.value());
        String errorMessage = e.getMessage();
        int colonIndex = errorMessage.indexOf(':');
        errorResponse.setErrors(errorMessage.substring(0, colonIndex));
        logger.warn(errorMessage);
        logger.warn(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse<Map<String, String>> errorResponse = new ErrorResponse<>();
        errorResponse.setHttpCode(HttpStatus.BAD_REQUEST.value());
        Map<String, String> errorMessage = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMessage.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        errorResponse.setErrors(errorMessage);
        logger.warn(e.getMessage());
        logger.warn(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
