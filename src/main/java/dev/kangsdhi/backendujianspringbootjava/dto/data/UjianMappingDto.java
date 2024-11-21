package dev.kangsdhi.backendujianspringbootjava.dto.data;

import dev.kangsdhi.backendujianspringbootjava.enums.StatusPertanyaan;
import lombok.Data;

import java.util.UUID;

@Data
public class UjianMappingDto {
    private UUID id_bank;
    private String pertanyaan;
    private String gambar_pertanyaan;
    private String pilihan_a;
    private String pilihan_b;
    private String pilihan_c;
    private String pilihan_d;
    private String pilihan_e;
    private String jawaban;
    private StatusPertanyaan status_pertanyaan;
}
