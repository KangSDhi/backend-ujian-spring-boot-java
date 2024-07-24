package dev.kangsdhi.backendujianspringbootjava.database.seeder;

import dev.kangsdhi.backendujianspringbootjava.dto.seeder.SiswaBaruSeeder;
import dev.kangsdhi.backendujianspringbootjava.entities.*;
import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.KelasRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SiswaSeeder {

    @Autowired
    private TingkatRepository tingkatRepository;

    @Autowired
    private JurusanRepository jurusanRepository;

    @Autowired
    private KelasRepository kelasRepository;

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void seedSiswa() {
        List<Pengguna> dataSiswa = penggunaRepository.findByRolePengguna(RolePengguna.SISWA);
        if (dataSiswa.toArray().length == 0){
            List<SiswaBaruSeeder> siswaBaruSeederList = new ArrayList<>(List.of(
                    new SiswaBaruSeeder(
                            "X-TKJ-1-2343",
                            "Sigit Boworaharjo",
                            "qwerty",
                            "X",
                            "Teknik Komputer dan Jaringan"),
                    new SiswaBaruSeeder(
                            "X-TSP-1-34333",
                            "Dhini Aprilia Budiarti",
                            "qwerty3",
                            "XII",
                            "Teknik Geomatika")
            ));

            for (SiswaBaruSeeder siswaBaruSeederItem : siswaBaruSeederList){
                Tingkat tingkat = tingkatRepository.findTingkatByTingkat(siswaBaruSeederItem.getTingkatSiswa());
                Jurusan jurusan = jurusanRepository.findJurusanByJurusan(siswaBaruSeederItem.getJurusanSiswa());
                Kelas kelas = kelasRepository.findByTingkatAndJurusan(tingkat, jurusan);

                Pengguna siswaBaru = new Pengguna();
                siswaBaru.setIdSiswa(siswaBaruSeederItem.getIdSiswa());
                siswaBaru.setNamaPengguna(siswaBaruSeederItem.getNamaSiswa());
                siswaBaru.setPasswordPengguna(passwordEncoder.encode(siswaBaruSeederItem.getPasswordSiswa()));
                siswaBaru.setPasswordPlain(siswaBaruSeederItem.getPasswordSiswa());
                siswaBaru.setKelas(kelas);
                siswaBaru.setRolePengguna(RolePengguna.SISWA);
                penggunaRepository.save(siswaBaru);
                System.out.println("Membuat Data Siswa Baru : "+ siswaBaruSeederItem.getNamaSiswa()+" ✅");
            }
        }
    }
}
