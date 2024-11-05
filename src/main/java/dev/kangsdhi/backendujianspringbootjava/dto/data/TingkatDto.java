package dev.kangsdhi.backendujianspringbootjava.dto.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TingkatDto {
    private String id;
    private String namaTingkat;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
