package dev.kangsdhi.backendujianspringbootjava.database.seeder;

import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JurusanSeeder {

    @Autowired
    private JurusanRepository jurusanRepository;

    public void seedJurusan(){
        String[] dataJurusans = new String[]{
                "Teknik Konstruksi dan Properti",
                "Desain Pemodelan dan Informasi Bangunan",
                "Kimia Industri",
                "Teknik Geomatika",
                "Teknik Installasi Tenaga Listrik",
                "Teknik Komputer dan Jaringan",
                "Teknik Mekatronika",
                "Teknik Kendaraan Ringan Otomotif",
                "Teknik Pengelasan",
                "Teknik Elektronika Industri"
        };

        for (String dataJurusan : dataJurusans) {
            Jurusan jurusan = jurusanRepository.findJurusanByJurusan(dataJurusan);
            if (jurusan == null) {
                Jurusan jurusanBaru = new Jurusan();
                jurusanBaru.setJurusan(dataJurusan);
                jurusanRepository.save(jurusanBaru);
                System.out.println("Membuat Data Jurusan Baru : "+dataJurusan+" ✅");
            }
        }
    }
}
