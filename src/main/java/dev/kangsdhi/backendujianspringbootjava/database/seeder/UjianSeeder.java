package dev.kangsdhi.backendujianspringbootjava.database.seeder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kangsdhi.backendujianspringbootjava.dto.ListJawaban;
import dev.kangsdhi.backendujianspringbootjava.dto.UjianBaruSeeder;
import dev.kangsdhi.backendujianspringbootjava.entities.*;
import dev.kangsdhi.backendujianspringbootjava.enums.StatusPertanyaan;
import dev.kangsdhi.backendujianspringbootjava.enums.StatusUjian;
import dev.kangsdhi.backendujianspringbootjava.repository.BankSoalRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.SoalRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.UjianRepository;
import dev.kangsdhi.backendujianspringbootjava.utils.ConvertUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UjianSeeder {

    @Autowired
    private UjianRepository ujianRepository;

    @Autowired
    private SoalRepository soalRepository;

    @Autowired
    private BankSoalRepository bankSoalRepository;

    @Autowired
    private PenggunaRepository penggunaRepository;

    Logger logger = LoggerFactory.getLogger(UjianSeeder.class);

    ConvertUtils convertUtils = new ConvertUtils();

    public void seedUjian() {
        List<Ujian> dataUjian = ujianRepository.findAll();
        if (dataUjian.isEmpty()) {
            List<UjianBaruSeeder> dataUjianBaruSeeder = new ArrayList<>(List.of(
                    new UjianBaruSeeder(
                            "Matematika X",
                            convertUtils.convertStringToDatetimeOrTime("05-07-2024 07:00:00"),
                            StatusUjian.BELUM_MULAI,
                            "Sigit Boworaharjo"),
                    new UjianBaruSeeder(
                            "Matematika X",
                            convertUtils.convertStringToDatetimeOrTime("05-07-2024 07:00:00"),
                            StatusUjian.BELUM_MULAI,
                            "Dhini Aprilia Budiarti")
            ));

            for (UjianBaruSeeder ujianBaruSeederItem : dataUjianBaruSeeder) {
                Pengguna pengguna = penggunaRepository.findByNamaPenggunaAndRolePengguna(ujianBaruSeederItem.getNamaPengguna(), RolePengguna.SISWA);
                List<ListJawaban> listJawaban = new ArrayList<>();
                Soal soal = soalRepository.findByNamaSoal(ujianBaruSeederItem.getNamaSoal());
                List<BankSoal> bankSoal = bankSoalRepository.findBySoal(soal);
                for (BankSoal bankSoalItem : bankSoal) {

                    ListJawaban listJawabanBaru = new ListJawaban();
                    listJawabanBaru.setIdBank(bankSoalItem.getId());
                    listJawabanBaru.setPertanyaan(bankSoalItem.getPertanyaanBankSoal());
                    listJawabanBaru.setStatusPertanyaan(StatusPertanyaan.BELUM_DIJAWAB);

                    System.out.println(bankSoalItem.toString());

                    List<String> listPilihan = Arrays.asList(
                            bankSoalItem.getPilihanA(),
                            bankSoalItem.getPilihanB(),
                            bankSoalItem.getPilihanC(),
                            bankSoalItem.getPilihanD(),
                            bankSoalItem.getPilihanE()
                    );

                    System.out.println(Arrays.toString(listPilihan.toArray()));

                    Collections.shuffle(listPilihan);

                    System.out.println(Arrays.toString(listPilihan.toArray()));

                    listJawabanBaru.setPilihanA(listPilihan.getFirst());
                    listJawabanBaru.setPilihanB(listPilihan.get(1));
                    listJawabanBaru.setPilihanC(listPilihan.get(2));
                    listJawabanBaru.setPilihanD(listPilihan.get(3));
                    listJawabanBaru.setPilihanE(listPilihan.get(4));

                    listJawaban.add(listJawabanBaru);
                }

                Collections.shuffle(listJawaban);

                System.out.println(Arrays.toString(listJawaban.toArray()));
                String listJawabanJson = convertListJawabanToJson(listJawaban);

                System.out.println(listJawabanJson);
                Ujian ujian = new Ujian();
                ujian.setSoal(soal);
                ujian.setListJawabanUjian(listJawabanJson);
                ujian.setStatusUjian(StatusUjian.BELUM_MULAI);
                ujian.setPengguna(pengguna);
                ujian.setWaktuSelesaiUjian(ujianBaruSeederItem.getWaktuSelesaiUjian());
                ujianRepository.save(ujian);
                System.out.println("Membuat Data Ujian Baru : "+pengguna.getNamaPengguna()+" "+ujian.getSoal().getNamaSoal()+" ✅");
            }
        }
    }

    private String convertListJawabanToJson(List<ListJawaban> listJawaban){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(listJawaban);
        } catch (JsonProcessingException e){
            logger.error(e.getMessage());
            return null;
        }
    }
}
