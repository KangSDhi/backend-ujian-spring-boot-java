package dev.kangsdhi.backendujianspringbootjava.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SoalIdRequest {
    @NotNull(message = "Soal Id Kosong!")
    @NotBlank(message = "Soal Id Kosong!")
    private String soalId;
}
