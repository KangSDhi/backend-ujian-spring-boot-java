package dev.kangsdhi.backendujianspringbootjava.dto.response;

import lombok.Data;

@Data
public class ResponseWithMessage {
    private Integer httpCode;
    private String message;
}
