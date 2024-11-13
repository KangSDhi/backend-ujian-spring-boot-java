package dev.kangsdhi.backendujianspringbootjava.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SiswaItemBatchRequest {

    @NotNull(message = "Nama Siswa Kosong!")
    @NotBlank(message = "Nama Siswa Kosong!")
    private String nama_siswa;

    @NotNull(message = "ID Siswa Kosong!")
    @NotBlank(message = "ID Siswa Kosong!")
    private String id_siswa;

    @NotNull(message = "Kelas Siswa Kosong!")
    @NotBlank(message = "Kelas Siswa Kosong!")
    private String kelas;
}
