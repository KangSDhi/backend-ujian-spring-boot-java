package dev.kangsdhi.backendujianspringbootjava.database;

import dev.kangsdhi.backendujianspringbootjava.database.seeder.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
public class DatabaseSeeder {

    @Autowired
    TingkatSeeder tingkatSeeder;

    @Autowired
    JurusanSeeder jurusanSeeder;

    @Autowired
    KelasSeeder kelasSeeder;

    @Autowired
    AdminSeeder adminSeeder;

    @Autowired
    SiswaSeeder siswaSeeder;

    @Autowired
    SoalSeeder soalSeeder;

    @Autowired
    BankSoalSeeder bankSoalSeeder;

    @Autowired
    UjianSeeder ujianSeeder;

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        tingkatSeeder.seedTingkat();
        jurusanSeeder.seedJurusan();
        kelasSeeder.seedKelas();
        adminSeeder.seedAdmin();
        siswaSeeder.seedSiswa();
        soalSeeder.seedSoal();
        bankSoalSeeder.seedBankSoal();
//        ujianSeeder.seedUjian();
    }
}
