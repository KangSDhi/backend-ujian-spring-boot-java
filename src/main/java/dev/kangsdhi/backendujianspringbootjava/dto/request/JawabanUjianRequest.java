package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.enums.StatusPertanyaan;
import lombok.Data;

@Data
public class JawabanUjianRequest {
    private String idSoal;
    private String idBank;
    private String jawaban;
    private StatusPertanyaan statusPertanyaan;
}
