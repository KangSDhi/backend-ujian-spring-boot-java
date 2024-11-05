package dev.kangsdhi.backendujianspringbootjava.dto.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JurusanDto {
    private String id;
    private String namaJurusan;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
