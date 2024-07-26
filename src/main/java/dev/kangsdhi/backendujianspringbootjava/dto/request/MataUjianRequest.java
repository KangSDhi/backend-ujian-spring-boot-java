package dev.kangsdhi.backendujianspringbootjava.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MataUjianRequest {

    @NotBlank(message = "Tingkat Kosong!")
    @NotNull(message = "Tingkat Kosong!")
    private String tingkat;

    @NotBlank(message = "Jurusan Kosong!")
    @NotNull(message = "Jurusan Kosong!")
    private String jurusan;
}
