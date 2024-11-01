package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.NamaSoalUnique;
import dev.kangsdhi.backendujianspringbootjava.validators.WaktuSoalValid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SoalCreateRequest extends SoalRequest{
    
    @NamaSoalUnique(message = "Nama Soal Sudah Terdaftar!")
    @NotNull(message = "Nama Soal Kosong!")
    @NotBlank(message = "Nama Soal Kosong!")
    private String namaSoal;
    
}
