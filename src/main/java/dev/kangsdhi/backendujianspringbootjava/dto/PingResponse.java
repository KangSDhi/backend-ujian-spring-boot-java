package dev.kangsdhi.backendujianspringbootjava.dto;

import lombok.Data;

@Data
public class PingResponse<T> {
    private int httpCode;
    private String message;
    private T data;
}
