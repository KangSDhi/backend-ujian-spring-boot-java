package dev.kangsdhi.backendujianspringbootjava.dto.data;

import dev.kangsdhi.backendujianspringbootjava.enums.AcakSoal;
import dev.kangsdhi.backendujianspringbootjava.enums.TipeSoal;
import lombok.Data;

@Data
public class SoalDto {
    private String id;
    private String namaSoal;
    private String tingkat;
    private String jurusan;
    private Integer butirSoal;
    private String durasiSoal;
    private AcakSoal acakSoal;
    private TipeSoal tipeSoal;
    private String waktuMulaiSoal;
    private String waktuSelesaiSoal;
}
