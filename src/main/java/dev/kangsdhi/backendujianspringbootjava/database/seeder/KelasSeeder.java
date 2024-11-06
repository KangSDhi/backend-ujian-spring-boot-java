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
import java.util.Map;

@Service
public class KelasSeeder {

    @Autowired
    TingkatRepository tingkatRepository;

    @Autowired
    JurusanRepository jurusanRepository;

    @Autowired
    KelasRepository kelasRepository;

    public void seedKelas() {
        int countDataKelas = kelasRepository.findAll().toArray().length;
        if (countDataKelas == 0) {

            List<Tingkat> tingkats = tingkatRepository.findAll();
            List<Jurusan> jurusans = jurusanRepository.findAll();

            Map<String, String> jurusanMap = Map.of(
                    "Teknik Konstruksi dan Properti", "TKP",
                    "Desain Pemodelan dan Informasi Bangunan", "DPIB",
                    "Kimia Industri", "KI",
                    "Teknik Geomatika", "GMT",
                    "Teknik Installasi Tenaga Listrik", "TITL",
                    "Teknik Komputer dan Jaringan", "TKJ",
                    "Teknik Mekatronika", "MEKA",
                    "Teknik Kendaraan Ringan Otomotif", "TKRO",
                    "Teknik Pengelasan", "TP",
                    "Teknik Elektronika Industri", "TEI"
            );

            for (Tingkat itemTingkat : tingkats) {
                for (Jurusan itemJurusan : jurusans) {
                    String jurusan = jurusanMap.getOrDefault(itemJurusan.getJurusan(), "");

                    int classCount = switch (jurusan) {
                        case "TKRO" -> 3;
                        case "KI", "MEKA", "TP" -> 1;
                        default -> 2;
                    };

                    for (int i = 0; i < classCount; i++) {
                        Kelas kelasBaru = new Kelas();
                        String classSuffix = classCount > 1 ? "-" + (i + 1) : "";
                        kelasBaru.setKelas(itemTingkat.getTingkat() + "-" + jurusan + classSuffix);
                        kelasBaru.setTingkat(itemTingkat);
                        kelasBaru.setJurusan(itemJurusan);
                        kelasRepository.save(kelasBaru);
                        System.out.println("Membuat Data Kelas Baru: " + itemTingkat.getTingkat() + "-" + jurusan + classSuffix + " ✅");
                    }
                }
            }
        }
    }
}
