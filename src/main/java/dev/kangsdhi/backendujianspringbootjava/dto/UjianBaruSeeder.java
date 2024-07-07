package dev.kangsdhi.backendujianspringbootjava.dto;

import dev.kangsdhi.backendujianspringbootjava.enums.StatusUjian;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class UjianBaruSeeder {

    private String namaSoal;
    private Date waktuSelesaiUjian;
    private StatusUjian statusUjian;
    private String namaPengguna;

}
