package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.IDSiswaUnique;
import dev.kangsdhi.backendujianspringbootjava.validators.NamaSiswaUnique;
import dev.kangsdhi.backendujianspringbootjava.validators.PasswordMatch;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@PasswordMatch(message = "Konfirmasi Password Tidak Sama Dengan Password!")
public class SiswaCreateRequest extends SiswaRequest {

    @NotBlank(message = "ID Siswa Kosong!")
    @NotNull(message = "ID Siswa Kosong!")
    @IDSiswaUnique(message = "ID Siswa Sudah Terdaftar!")
    private String id_siswa;

    @NotBlank(message = "Nama Siswa Kosong!")
    @NotNull(message = "Nama Siswa Kosong!")
    @NamaSiswaUnique(message = "Nama Siswa Sudah Terdaftar!")
    private String nama_siswa;

    @NotBlank(message = "Password Kosong!")
    @NotNull(message = "Password Kosong!")
    @Min(value = 8, message = "Password Minimal 8 Karakter!")
    private String password;

    @NotBlank(message = "Konfirmasi Password Kosong!")
    @NotNull(message = "Konfirmasi Password Kosong!")
    @Min(value = 8, message = "Konfirmasi Password Minimal 8 Karakter!")
    private String konfirmasi_password;
}
