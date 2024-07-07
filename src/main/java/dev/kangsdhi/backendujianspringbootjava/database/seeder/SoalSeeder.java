package dev.kangsdhi.backendujianspringbootjava.database.seeder;

import dev.kangsdhi.backendujianspringbootjava.dto.SoalBaruSeeder;
import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import dev.kangsdhi.backendujianspringbootjava.entities.Soal;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import dev.kangsdhi.backendujianspringbootjava.enums.AcakSoal;
import dev.kangsdhi.backendujianspringbootjava.enums.TipeSoal;
import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.SoalRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import dev.kangsdhi.backendujianspringbootjava.utils.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SoalSeeder {

    @Autowired
    private SoalRepository soalRepository;

    @Autowired
    private TingkatRepository tingkatRepository;

    @Autowired
    private JurusanRepository jurusanRepository;

    ConvertUtils convertUtils = new ConvertUtils();

    public void seedSoal(){
        List<Soal> dataSoal = soalRepository.findAll();
        if (dataSoal.isEmpty()){
            List<SoalBaruSeeder> dataSoalBaruSeeder = new ArrayList<>(List.of(
                    new SoalBaruSeeder(
                            "Matematika X",
                            56,
                            AcakSoal.ACAK,
                            convertUtils.convertStringToDatetimeOrTime("05-07-2024 07:00:00"),
                            convertUtils.convertStringToDatetimeOrTime("02:00:00"),
                            convertUtils.convertStringToDatetimeOrTime("05-07-2024 09:00:00"),
                            "X",
                            null,
                            TipeSoal.PILIHAN_GANDA),
                    new SoalBaruSeeder(
                            "Dasar Kejuruan TKJ",
                            40,
                            AcakSoal.ACAK,
                            convertUtils.convertStringToDatetimeOrTime("05-07-2024 10:00:00"),
                            convertUtils.convertStringToDatetimeOrTime("03:00:00"),
                            convertUtils.convertStringToDatetimeOrTime("05-07-2024 13:00:00"),
                            "X",
                            "Teknik Komputer dan Jaringan",
                            TipeSoal.PILIHAN_GANDA)
            ));

            for (SoalBaruSeeder soalBaruSeederItem : dataSoalBaruSeeder){
                Tingkat tingkat = tingkatRepository.findTingkatByTingkat(soalBaruSeederItem.getTingkat());
                Jurusan jurusan = jurusanRepository.findJurusanByJurusan(soalBaruSeederItem.getJurusan());

                Soal soalBaru = new Soal();
                soalBaru.setNamaSoal(soalBaruSeederItem.getNamaSoal());
                soalBaru.setButirSoal(soalBaruSeederItem.getButirSoal());
                soalBaru.setAcakSoal(soalBaruSeederItem.getAcakSoal());
                soalBaru.setWaktuMulaiSoal(soalBaruSeederItem.getWaktuMulaiSoal());
                soalBaru.setDurasiSoal(soalBaruSeederItem.getDurasiSoal());
                soalBaru.setWaktuSelesaiSoal(soalBaruSeederItem.getWaktuSelesaiSoal());
                soalBaru.setTingkat(tingkat);
                soalBaru.setJurusan(jurusan);
                soalBaru.setTipeSoal(soalBaruSeederItem.getTipeSoal());
                soalRepository.save(soalBaru);
                System.out.println("Membuat Data Soal Baru : "+soalBaru.getNamaSoal()+" ✅");
            }
        }
    }
}
