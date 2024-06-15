package dev.kangsdhi.backendujianspringbootjava.database;

import dev.kangsdhi.backendujianspringbootjava.database.seeder.JurusanSeeder;
import dev.kangsdhi.backendujianspringbootjava.database.seeder.KelasSeeder;
import dev.kangsdhi.backendujianspringbootjava.database.seeder.TingkatSeeder;
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

    @EventListener
    public void seed(ContextRefreshedEvent event) {
        tingkatSeeder.createDataTingkat();
        jurusanSeeder.createDataJurusan();
        kelasSeeder.createDataKelas();
    }
}
