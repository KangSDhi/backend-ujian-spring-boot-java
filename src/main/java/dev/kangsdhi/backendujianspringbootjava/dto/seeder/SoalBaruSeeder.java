package dev.kangsdhi.backendujianspringbootjava.dto.seeder;

import dev.kangsdhi.backendujianspringbootjava.enums.AcakSoal;
import dev.kangsdhi.backendujianspringbootjava.enums.TipeSoal;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@AllArgsConstructor
@Data
public class SoalBaruSeeder {
    private String namaSoal;
    private String tokenSoal;
    private Integer butirSoal;
    private AcakSoal acakSoal;
    private Date waktuMulaiSoal;
    private Date durasiSoal;
    private Date waktuSelesaiSoal;
    private String tingkat;
    private String jurusan;
    private TipeSoal tipeSoal;
}
