package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.NamaJurusanUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class JurusanCreateRequest {

    @NotBlank(message = "Nama Jurusan Kosong!")
    @NotNull(message = "Nama Jurusan Kosong!")
    @NamaJurusanUnique(message = "Nama Jurusan Telah Terdaftar!")
    private String namaJurusan;
}
