package dev.kangsdhi.backendujianspringbootjava.database.seeder;

import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TingkatSeeder {

    @Autowired
    private TingkatRepository tingkatRepository;

    public void createDataTingkat(){
        String[] dataTingkats = new String[]{
                "X",
                "XI",
                "XII"
        };

        for (String dataTingkat : dataTingkats) {
            Tingkat tingkat = tingkatRepository.findTingkatByTingkat(dataTingkat);
            if (tingkat == null) {
                Tingkat tingkatBaru = new Tingkat();
                tingkatBaru.setTingkat(dataTingkat);
                tingkatRepository.save(tingkatBaru);
                System.out.println("Membuat Data Tingkat Baru : "+dataTingkat+" ✅");
            }
        }
    }
}
