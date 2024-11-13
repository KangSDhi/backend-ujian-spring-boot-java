package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.validators.PasswordMatch;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@PasswordMatch(message = "Konfirmasi Password Tidak Sama Dengan Password!")
public class SiswaCreateRequest {

    @NotBlank(message = "ID Siswa Kosong!")
    @NotNull(message = "ID Siswa Kosong!")
    private String id_siswa;

    @NotBlank(message = "Nama Siswa Kosong!")
    @NotBlank(message = "Nama Siswa Kosong!")
    private String nama_siswa;

    @NotBlank(message = "Kelas Kosong!")
    @NotNull(message = "Kelas Kosong!")
    private String kelas;

    @NotBlank(message = "Password Kosong!")
    @NotNull(message = "Password Kosong!")
    @Min(value = 8, message = "Password Minimal 8 Karakter!")
    private String password;

    @NotBlank(message = "Konfirmasi Password Kosong!")
    @NotNull(message = "Konfirmasi Password Kosong!")
    @Min(value = 8, message = "Konfirmasi Password Minimal 8 Karakter!")
    private String konfirmasi_password;
}
