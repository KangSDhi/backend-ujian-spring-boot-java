package dev.kangsdhi.backendujianspringbootjava.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class BankSoalRequest {
    @NotBlank(message = "Id Soal Kosong!")
    @NotNull(message = "Id Soal Kosong!")
    @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
        message = "Format Id Soal Salah!")
    private String id_soal;
    @NotBlank(message = "Pertanyaan Kosong!")
    @NotNull(message = "Pertanyaan Kosong!")
    private String pertanyaan;
    private String gambar_pertanyaan;
    @NotBlank(message = "Pilihan A Kosong!")
    @NotNull(message = "Pilihan A Kosong!")
    private String pilihan_a;
    @NotBlank(message = "Pilihan B Kosong!")
    @NotNull(message = "Pilihan B Kosong!")
    private String pilihan_b;
    @NotBlank(message = "Pilihan C Kosong!")
    @NotNull(message = "Pilihan C Kosong!")
    private String pilihan_c;
    @NotBlank(message = "Pilihan D Kosong!")
    @NotNull(message = "Pilihan D Kosong!")
    private String pilihan_d;
    @NotBlank(message = "Pilihan E Kosong!")
    @NotNull(message = "Pilihan E Kosong!")
    private String pilihan_e;
    @NotNull(message = "Nilai A Kosong!")
    private Float nilai_a;
    @NotNull(message = "Nilai B Kosong!")
    private Float nilai_b;
    @NotNull(message = "Nilai C Kosong!")
    private Float nilai_c;
    @NotNull(message = "Nilai D Kosong!")
    private Float nilai_d;
    @NotNull(message = "Nilai E Kosong!")
    private Float nilai_e;
}
