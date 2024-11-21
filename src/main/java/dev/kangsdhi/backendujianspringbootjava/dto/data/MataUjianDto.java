package dev.kangsdhi.backendujianspringbootjava.dto.data;

import dev.kangsdhi.backendujianspringbootjava.enums.AcakSoal;
import dev.kangsdhi.backendujianspringbootjava.enums.StatusMataUjian;
import lombok.Data;

@Data
public class MataUjianDto {
    private String id;
    private String nama_soal;
    private Integer butir_soal;
    private AcakSoal acak_soal;
    private String waktu_mulai_soal;
    private String waktu_selesai_soal;
    private StatusMataUjian status_mata_ujian;
}
