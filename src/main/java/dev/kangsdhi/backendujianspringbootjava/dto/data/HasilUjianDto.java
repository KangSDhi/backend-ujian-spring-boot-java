package dev.kangsdhi.backendujianspringbootjava.dto.data;

import lombok.Data;

import java.util.UUID;

@Data
public class HasilUjianDto {
    private UUID idNilaiUjian;
    private Float nilaiUjian;
}
