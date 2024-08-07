package dev.kangsdhi.backendujianspringbootjava.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SignInRequest {

    @NotBlank(message = "Email atau ID Siswa Kosong!")
    @NotNull(message = "Email atau ID Siswa Kosong!")
    private String emailOrIdSiswa;

    @NotBlank(message = "Password Kosong!")
    @NotNull(message = "Password Kosong!")
    private String password;
}