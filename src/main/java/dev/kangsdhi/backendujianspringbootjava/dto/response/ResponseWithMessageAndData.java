package dev.kangsdhi.backendujianspringbootjava.dto.response;

import lombok.Data;

@Data
public class ResponseWithMessageAndData<T> {
    private Integer http_code;
    private String message;
    private T data;
}
