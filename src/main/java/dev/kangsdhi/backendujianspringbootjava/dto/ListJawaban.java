package dev.kangsdhi.backendujianspringbootjava.dto;

import dev.kangsdhi.backendujianspringbootjava.enums.StatusPertanyaan;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
public class ListJawaban {
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
