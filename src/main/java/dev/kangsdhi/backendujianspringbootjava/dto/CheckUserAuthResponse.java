package dev.kangsdhi.backendujianspringbootjava.dto;

import lombok.Data;

@Data
public class CheckUserAuthResponse<T> {
    private int httpCode;
    private T data;
}
