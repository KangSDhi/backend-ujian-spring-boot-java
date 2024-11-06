package dev.kangsdhi.backendujianspringbootjava.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SiswaRequest {

    @NotNull(message = "Nama Siswa Kosong!")
    @NotBlank(message = "Nama Siswa Kosong!")
    private String namaSiswa;

    @NotNull(message = "ID Siswa Kosong!")
    @NotBlank(message = "ID Siswa Kosong!")
    private String idSiswa;

    @NotNull(message = "Kelas Siswa Kosong!")
    @NotBlank(message = "Kelas Siswa Kosong!")
    private String kelasSiswa;
}
