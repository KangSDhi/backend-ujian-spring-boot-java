package dev.kangsdhi.backendujianspringbootjava.dto.response;

import lombok.Data;

@Data
public class ResponseError<T> {
    private Integer httpCode;
    private T errors;
}
