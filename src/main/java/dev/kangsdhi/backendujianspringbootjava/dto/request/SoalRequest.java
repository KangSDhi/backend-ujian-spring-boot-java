package dev.kangsdhi.backendujianspringbootjava.dto.request;

import dev.kangsdhi.backendujianspringbootjava.enums.AcakSoal;
import dev.kangsdhi.backendujianspringbootjava.enums.TipeSoal;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class SoalRequest {
    @NotNull(message = "Nama Soal Kosong!")
    @NotBlank(message = "Nama Soal Kosong!")
    private String namaSoal;
    @NotNull(message = "Tingkat Soal Kosong!")
    @NotBlank(message = "Tingkat Soal Kosong!")
    private String tingkatSoal;
    private String jurusanSoal;
    @NotNull(message = "Acak Soal Kosong!")
    private AcakSoal acakSoal;
    @NotNull(message = "Butir Soal Kosong!")
    private Integer butirSoal;
    @NotNull(message = "Tipe Soal Kosong!")
    private TipeSoal tipeSoal;
    @NotNull(message = "Durasi Soal Kosong!")
    @NotBlank(message = "Durasi Soal Kosong!")
    @Pattern(regexp = "^\\d{2}:\\d{2}:\\d{2}$", message = "Durasi soal harus dalam format HH:mm:ss!")
    private String durasiSoal;
    @NotNull(message = "Waktu Mulai Soal Kosong!")
    @NotBlank(message = "Waktu Mulai Soal Kosong!")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}$", message = "Waktu Mulai Soal harus dalam format dd:MM:YYYY HH:mm:ss!")
    private String waktuMulaiSoal;
    @NotNull(message = "Waktu Selesai Soal Kosong!")
    @NotBlank(message = "Waktu Selesai Soal Kosong!")
    @Pattern(regexp = "^\\d{2}-\\d{2}-\\d{4} \\d{2}:\\d{2}:\\d{2}$", message = "Waktu Selesai Soal harus dalam format dd:MM:YYYY HH:mm:ss!")
    private String waktuSelesaiSoal;
}
