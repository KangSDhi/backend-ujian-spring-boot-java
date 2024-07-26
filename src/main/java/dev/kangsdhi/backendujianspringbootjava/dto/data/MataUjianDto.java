package dev.kangsdhi.backendujianspringbootjava.dto.data;

import dev.kangsdhi.backendujianspringbootjava.enums.AcakSoal;
import dev.kangsdhi.backendujianspringbootjava.enums.StatusMataUjian;
import lombok.Data;

@Data
public class MataUjianDto {
    private String idSoal;
    private String namaSoal;
    private Integer butirSoal;
    private AcakSoal acakSoal;
    private String waktuMulaiSoal;
    private String waktuSelesaiSoal;
    private StatusMataUjian statusMataUjian;
}
