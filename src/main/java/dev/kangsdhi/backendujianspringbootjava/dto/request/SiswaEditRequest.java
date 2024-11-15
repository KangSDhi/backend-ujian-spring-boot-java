package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.IDSiswaExistById;
import dev.kangsdhi.backendujianspringbootjava.validators.NamaSiswaExistById;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@IDSiswaExistById(message = "ID Siswa Sudah Terdaftar!")
@NamaSiswaExistById(message = "Nama Siswa Sudah Terdaftar!")
public class SiswaEditRequest extends SiswaRequest{

    @NotBlank(message = "ID Tidak Boleh Kosong!")
    @NotNull(message = "ID Tidak Boleh Kosong!")
    private String id;

    @NotBlank(message = "ID Siswa Tidak Boleh Kosong!")
    @NotNull(message = "ID Siswa Tidak Boleh Kosong!")
    private String id_siswa;

    @NotBlank(message = "Nama Siswa Tidak Boleh Kosong!")
    @NotNull(message = "Nama Siswa Tidak Boleh Kosong!")
    private String nama_siswa;

    private String password;

    private String konfirmasi_password;
}
