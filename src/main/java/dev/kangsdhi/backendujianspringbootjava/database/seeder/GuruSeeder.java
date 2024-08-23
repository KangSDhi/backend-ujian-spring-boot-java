package dev.kangsdhi.backendujianspringbootjava.database.seeder;

import dev.kangsdhi.backendujianspringbootjava.dto.seeder.GuruBaruSeeder;
import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import dev.kangsdhi.backendujianspringbootjava.entities.Pengguna;
import dev.kangsdhi.backendujianspringbootjava.entities.RolePengguna;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GuruSeeder {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private TingkatRepository tingkatRepository;

    @Autowired
    private JurusanRepository jurusanRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void seedGuru(){
        List<Pengguna> guru = penggunaRepository.findByRolePengguna(RolePengguna.GURU);
        if(guru.isEmpty()){
            List<GuruBaruSeeder> guruBaruSeederList = new ArrayList<>(List.of(
                    new GuruBaruSeeder(
                            "Dhini Guru",
                            "dhiniguru@gmail.com",
                            "ytrewq"
                    ),
                    new GuruBaruSeeder(
                            "Kang Guru",
                            "kangguru@gmail.com",
                            "123456"
                    )
            ));

            for (GuruBaruSeeder guruSeederItem : guruBaruSeederList) {
                Pengguna guruBaru = new Pengguna();
                guruBaru.setNamaPengguna(guruSeederItem.getNamaGuru());
                guruBaru.setEmailPengguna(guruSeederItem.getEmailGuru());
                guruBaru.setPasswordPengguna(passwordEncoder.encode(guruSeederItem.getPasswordGuru()));
                guruBaru.setRolePengguna(RolePengguna.GURU);
                penggunaRepository.save(guruBaru);
                System.out.println("Membuat Data Guru Baru ✅");
            }
        }
    }
}
