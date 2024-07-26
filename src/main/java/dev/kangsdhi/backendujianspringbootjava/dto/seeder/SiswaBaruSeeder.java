package dev.kangsdhi.backendujianspringbootjava.dto.seeder;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class SiswaBaruSeeder {
    private String idSiswa;
    private String namaSiswa;
    private String passwordSiswa;
    private String tingkatSiswa;
    private String jurusanSiswa;
}
