package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.NamaJurusanUniqueExistById;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NamaJurusanUniqueExistById(message = "Nama Jurusan Sudah Terdaftar!")
public class JurusanEditRequest {

    @NotBlank(message = "ID Jurusan Kosong!")
    @NotNull(message = "ID Jurusan Kosong!")
    private String id;

    @NotBlank(message = "Nama Jurusan Kosong!")
    @NotNull(message = "Nama Jurusan Kosong!")
    private String nama_jurusan;
}
