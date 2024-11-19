package dev.kangsdhi.backendujianspringbootjava.dto.response;

import lombok.Data;

@Data
public class ResponseError<T> {
    private Integer http_code;
    private T errors;
}
