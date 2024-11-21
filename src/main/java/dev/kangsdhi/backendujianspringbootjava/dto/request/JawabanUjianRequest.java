package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.enums.StatusPertanyaan;
import lombok.Data;

@Data
public class JawabanUjianRequest {
    private String id_soal;
    private String id_bank;
    private String jawaban;
    private StatusPertanyaan status_pertanyaan;
}
