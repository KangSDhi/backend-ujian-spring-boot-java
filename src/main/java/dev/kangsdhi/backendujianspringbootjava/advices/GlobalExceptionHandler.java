package dev.kangsdhi.backendujianspringbootjava.advices;

import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseError;
import jakarta.persistence.EntityNotFoundException;
import org.apache.coyote.BadRequestException;
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

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @ExceptionHandler(NoSuchElementException.class)
    protected ResponseEntity<ResponseError<String>> handleNoSuchElementException(NoSuchElementException e) {
        ResponseError<String> responseError = new ResponseError<>();
        responseError.setHttpCode(HttpStatus.NOT_FOUND.value());
        responseError.setErrors(e.getMessage());
        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    protected ResponseEntity<ResponseError<String>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        ResponseError<String> responseError = new ResponseError<>();
        responseError.setHttpCode(HttpStatus.INTERNAL_SERVER_ERROR.value());

        String errorMessage = e.getMessage();
        String duplicateData = extractDuplicateData(errorMessage);

        responseError.setErrors(duplicateData != null ? duplicateData : errorMessage);
        return new ResponseEntity<>(responseError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ResponseError<String>> handleEntityNotFoundException(EntityNotFoundException e) {
        ResponseError<String> responseError = new ResponseError<>();
        responseError.setHttpCode(HttpStatus.NOT_FOUND.value());
        responseError.setErrors(e.getMessage());
        return new ResponseEntity<>(responseError, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BadRequestException.class)
    protected ResponseEntity<ResponseError<String>> handleBadRequestException(BadRequestException e) {
        ResponseError<String> responseError = new ResponseError<>();
        responseError.setHttpCode(HttpStatus.BAD_REQUEST.value());
        responseError.setErrors(e.getMessage());
        return new ResponseEntity<>(responseError, HttpStatus.BAD_REQUEST);
    }

    private String extractDuplicateData(String errorMessage) {
        Pattern pattern = Pattern.compile("Duplicate entry '(.+?)' for key");
        Matcher matcher = pattern.matcher(errorMessage);
        if (matcher.find()) {
            return "Duplikasi Data " +matcher.group(1);
        }
        return null;
    }
}
