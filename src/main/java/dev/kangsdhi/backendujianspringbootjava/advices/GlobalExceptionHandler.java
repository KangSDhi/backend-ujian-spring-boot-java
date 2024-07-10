package dev.kangsdhi.backendujianspringbootjava.advices;

import dev.kangsdhi.backendujianspringbootjava.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        logger.warn(String.valueOf(errorResponse));
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


}
