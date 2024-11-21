package dev.kangsdhi.backendujianspringbootjava.dto.data;

import dev.kangsdhi.backendujianspringbootjava.enums.AcakSoal;
import dev.kangsdhi.backendujianspringbootjava.enums.TipeSoal;
import lombok.Data;

@Data
public class SoalDto {
    private String id;
    private String nama_soal;
    private String tingkat;
    private String jurusan;
    private Integer butir_soal;
    private String durasi_soal;
    private AcakSoal acak_soal;
    private TipeSoal tipe_soal;
    private String waktu_mulai_soal;
    private String waktu_selesai_soal;
}
