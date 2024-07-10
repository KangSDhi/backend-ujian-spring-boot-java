package dev.kangsdhi.backendujianspringbootjava.advices;

import dev.kangsdhi.backendujianspringbootjava.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

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
    protected ResponseEntity<ErrorResponse<String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ErrorResponse<String> errorResponse = new ErrorResponse<>();
        errorResponse.setHttpCode(HttpStatus.BAD_REQUEST.value());
        String errorMessage = e.getBindingResult().getAllErrors().getFirst().getDefaultMessage();
        errorResponse.setErrors(errorMessage);
        logger.warn(e.getMessage());
        logger.warn(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }


}
