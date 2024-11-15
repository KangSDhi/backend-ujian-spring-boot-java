package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.CheckKelasExist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SiswaRequest {

    private String id_siswa;

    private String nama_siswa;

    @NotBlank(message = "Kelas Kosong!")
    @NotNull(message = "Kelas Kosong!")
    @CheckKelasExist(message = "Kelas Tidak Tersedia!")
    private String kelas;

    private String password;

    private String konfirmasi_password;
}
