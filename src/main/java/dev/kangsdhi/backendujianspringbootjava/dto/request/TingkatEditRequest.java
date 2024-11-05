package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.NamaTingkatUniqueExistById;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@NamaTingkatUniqueExistById(message = "Nama Tingkat Sudah Terdaftar!")
public class TingkatEditRequest {

    @NotBlank(message = "ID Tingkat Kosong!")
    @NotNull(message = "ID Tingkat Kosong!")
    private String idTingkat;

    @NotBlank(message = "Nama Tingkat Kosong!")
    @NotNull(message = "Nama Tingkat Kosong!")
    private String namaTingkat;
}
