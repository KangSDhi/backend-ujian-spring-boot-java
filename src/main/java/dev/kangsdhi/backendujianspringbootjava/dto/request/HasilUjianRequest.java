package dev.kangsdhi.backendujianspringbootjava.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class HasilUjianRequest {

    @NotBlank(message = "Id Soal Kosong!")
    @NotNull(message = "Id Soal Kosong!")
    private String idSoal;
}
