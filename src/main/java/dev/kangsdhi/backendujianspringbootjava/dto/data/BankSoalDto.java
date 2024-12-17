package dev.kangsdhi.backendujianspringbootjava.dto.data;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class BankSoalDto {
    private UUID id;
    private UUID soal_id;
    private String pertanyaan;
    private String gambar_pertanyaan;
    private String pilihan_a;
    private String pilihan_b;
    private String pilihan_c;
    private String pilihan_d;
    private String pilihan_e;
    private Float nilai_a;
    private Float nilai_b;
    private Float nilai_c;
    private Float nilai_d;
    private Float nilai_e;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
