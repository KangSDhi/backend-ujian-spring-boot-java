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
        String waktuMulaiPagiString = "23-07-2024 07:00:00";
        String waktuSelesaiPagiString = "23-07-2024 08:00:00";
        String waktuMulaiSiangString = "23-07-2024 11:00:00";
        String waktuSelesaiSiangString = "23-07-2024 13:00:00";
        String waktuMulaiSoreString = "23-07-2024 14:00:00";
        String waktuSelesaiSoreString = "23-07-2024 15:00:00";
        if (dataSoal.isEmpty()){
            List<SoalBaruSeeder> dataSoalBaruSeeder = new ArrayList<>(List.of(
                    new SoalBaruSeeder(
                            "Matematika X",
                            56,
                            AcakSoal.ACAK,
                            convertUtils.convertStringToDatetimeOrTime(waktuMulaiPagiString),
                            convertUtils.convertStringToDatetimeOrTime("02:00:00"),
                            convertUtils.convertStringToDatetimeOrTime(waktuSelesaiPagiString),
                            "X",
                            null,
                            TipeSoal.PILIHAN_GANDA),
                    new SoalBaruSeeder(
                            "Dasar Kejuruan TKJ",
                            40,
                            AcakSoal.ACAK,
                            convertUtils.convertStringToDatetimeOrTime(waktuMulaiSiangString),
                            convertUtils.convertStringToDatetimeOrTime("03:00:00"),
                            convertUtils.convertStringToDatetimeOrTime(waktuSelesaiSiangString),
                            "X",
                            "Teknik Komputer dan Jaringan",
                            TipeSoal.PILIHAN_GANDA),
                    new SoalBaruSeeder(
                            "Konsentrasi Kejuruan GMT",
                            40,
                            AcakSoal.ACAK,
                            convertUtils.convertStringToDatetimeOrTime(waktuMulaiPagiString),
                            convertUtils.convertStringToDatetimeOrTime("03:00:00"),
                            convertUtils.convertStringToDatetimeOrTime(waktuSelesaiPagiString),
                            "XII",
                            "Teknik Geomatika",
                            TipeSoal.PILIHAN_GANDA),
                    new SoalBaruSeeder(
                            "Pilihan GMT Kejuruan GMT",
                            40,
                            AcakSoal.ACAK,
                            convertUtils.convertStringToDatetimeOrTime(waktuMulaiSoreString),
                            convertUtils.convertStringToDatetimeOrTime("03:00:00"),
                            convertUtils.convertStringToDatetimeOrTime(waktuSelesaiSoreString),
                            "XII",
                            "Teknik Geomatika",
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
