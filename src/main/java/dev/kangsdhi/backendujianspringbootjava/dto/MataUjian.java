package dev.kangsdhi.backendujianspringbootjava.dto;

import dev.kangsdhi.backendujianspringbootjava.enums.AcakSoal;
import dev.kangsdhi.backendujianspringbootjava.enums.StatusMataUjian;
import lombok.Data;

@Data
public class MataUjian {
    private String idSoal;
    private String namaSoal;
    private Integer butirSoal;
    private AcakSoal acakSoal;
    private String waktuMulaiSoal;
    private String waktuSelesaiSoal;
    private StatusMataUjian statusMataUjian;
}
