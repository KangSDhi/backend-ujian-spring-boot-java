package dev.kangsdhi.backendujianspringbootjava.advices;

import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(UsernameNotFoundException.class)
    protected ResponseEntity<ResponseError<String>> handleUsernameNotFoundException(UsernameNotFoundException e) {
        ResponseError<String> responseError = new ResponseError<>();
        responseError.setHttpCode(HttpStatus.NOT_FOUND.value());
        responseError.setErrors(e.getMessage());
        logger.warn(e.getMessage());
        logger.warn(String.valueOf(responseError));
        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ResponseError<String>> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        ResponseError<String> responseError = new ResponseError<>();
        responseError.setHttpCode(HttpStatus.BAD_REQUEST.value());
        String errorMessage = e.getMessage();
        int colonIndex = errorMessage.indexOf(':');
        responseError.setErrors(errorMessage.substring(0, colonIndex));
        logger.warn(errorMessage);
        logger.warn(String.valueOf(responseError));
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ResponseError<Map<String, String>>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ResponseError<Map<String, String>> responseError = new ResponseError<>();
        responseError.setHttpCode(HttpStatus.BAD_REQUEST.value());
        Map<String, String> errorMessage = new HashMap<>();
        for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
            errorMessage.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        responseError.setErrors(errorMessage);
        logger.warn(e.getMessage());
        logger.warn(String.valueOf(responseError));
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }


}
