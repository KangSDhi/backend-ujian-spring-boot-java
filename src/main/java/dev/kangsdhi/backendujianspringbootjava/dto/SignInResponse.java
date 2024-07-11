package dev.kangsdhi.backendujianspringbootjava.dto;

import lombok.Data;

@Data
public class SignInResponse<T> {
    private Integer httpCode;
    private String message;
    private T data;
}
