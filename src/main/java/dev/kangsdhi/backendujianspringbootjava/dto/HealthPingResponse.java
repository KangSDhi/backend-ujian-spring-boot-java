package dev.kangsdhi.backendujianspringbootjava.dto;

import lombok.Data;

@Data
public class HealthPingResponse {

    private int httpCode;

    private String message;
}
