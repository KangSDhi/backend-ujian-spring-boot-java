package dev.kangsdhi.backendujianspringbootjava.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CreatePenggunaAdminRequest {
    @NotNull(message = "Nama Admin Kosong!")
    @NotBlank(message = "Nama Admin Kosong!")
    private String nama_admin;
    @NotNull(message = "Email Admin Kosong!")
    @NotBlank(message = "Email Admin Kosong!")
    private String email;
    @NotNull(message = "Password Kosong!")
    @NotBlank(message = "Password Kosong!")
    private String password;
    @NotNull(message = "Password Konfirmasi Kosong!")
    @NotBlank(message = "Password Konfirmasi Kosong!")
    private String konfimasi_password;
}
