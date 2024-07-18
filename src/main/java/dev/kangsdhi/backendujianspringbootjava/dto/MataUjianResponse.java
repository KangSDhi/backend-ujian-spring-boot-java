package dev.kangsdhi.backendujianspringbootjava.dto;

import lombok.Data;

@Data
public class MataUjianResponse<T> {
    private Integer httpCode;
    private String message;
    private T data;
}
