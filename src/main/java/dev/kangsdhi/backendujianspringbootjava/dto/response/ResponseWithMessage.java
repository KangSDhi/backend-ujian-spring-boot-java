package dev.kangsdhi.backendujianspringbootjava.dto.response;

import lombok.Data;

@Data
public class ResponseWithMessage {
    private Integer http_code;
    private String message;
}
