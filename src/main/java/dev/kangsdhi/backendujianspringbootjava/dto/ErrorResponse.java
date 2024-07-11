package dev.kangsdhi.backendujianspringbootjava.dto;

import lombok.Data;

@Data
public class ErrorResponse<T> {
    private Integer httpCode;
    private T errors;
}
