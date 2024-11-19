package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.NamaTingkatUnique;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TingkatCreateRequest {

    @NotBlank(message = "Nama Tingkat Kosong!")
    @NotNull(message = "Nama Tingkat Kosong!")
    @NamaTingkatUnique(message = "Nama Tingkat Telah Terdaftar!")
    private String nama_tingkat;
}
