package dev.kangsdhi.backendujianspringbootjava.dto.data;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class JurusanDto {
    private String id;
    private String nama_jurusan;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
