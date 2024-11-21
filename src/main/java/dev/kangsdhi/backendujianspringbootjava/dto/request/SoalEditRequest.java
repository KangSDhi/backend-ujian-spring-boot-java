package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.NamaSoalUniqueExistById;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@NamaSoalUniqueExistById(message = "Nama Soal Sudah Terdaftar!")
public class SoalEditRequest extends SoalRequest {

    @NotNull(message = "Id Kosong!")
    @NotBlank(message = "Id Kosong!")
    private String id;

    @NotNull(message = "Nama Soal Kosong!")
    @NotBlank(message = "Nama Soal Kosong!")
    private String nama_soal;

}
