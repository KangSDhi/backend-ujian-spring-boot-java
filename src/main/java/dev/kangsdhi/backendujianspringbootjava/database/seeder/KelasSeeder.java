package dev.kangsdhi.backendujianspringbootjava.database.seeder;

import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import dev.kangsdhi.backendujianspringbootjava.entities.Kelas;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.KelasRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class KelasSeeder {

    @Autowired
    TingkatRepository tingkatRepository;

    @Autowired
    JurusanRepository jurusanRepository;

    @Autowired
    KelasRepository kelasRepository;

    public void createDataKelas() {
        int countDataKelas = kelasRepository.findAll().toArray().length;
        if (countDataKelas == 0){

            List<Tingkat> tingkats = tingkatRepository.findAll();

            for (Tingkat itemTingkat : tingkats){
                List<Jurusan> jurusans = jurusanRepository.findAll();

                for (Jurusan itemJurusan : jurusans){
                    Kelas kelasBaru = new Kelas();
                    kelasBaru.setTingkat(itemTingkat);
                    kelasBaru.setJurusan(itemJurusan);
                    kelasRepository.save(kelasBaru);
                    System.out.println("Membuat Data Kelas Baru : "+itemTingkat.getTingkat()+" "+itemJurusan.getJurusan()+" ✅");
                }
            }
        }
    }
}
