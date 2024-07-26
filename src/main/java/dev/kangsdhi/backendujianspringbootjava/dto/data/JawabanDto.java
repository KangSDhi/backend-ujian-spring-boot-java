package dev.kangsdhi.backendujianspringbootjava.dto.data;

import dev.kangsdhi.backendujianspringbootjava.enums.StatusPertanyaan;
import lombok.Data;

import java.util.UUID;

@Data
public class JawabanDto {
    private UUID idBank;
    private String pertanyaan;
    private String pilihanA;
    private String pilihanB;
    private String pilihanC;
    private String pilihanD;
    private String pilihanE;
    private String jawaban;
    private StatusPertanyaan statusPertanyaan;
}
