package dev.kangsdhi.backendujianspringbootjava.dto.response;

import lombok.Data;

@Data
public class ResponseWithMessageAndData<T> {
    private Integer httpCode;
    private String message;
    private T data;
}
