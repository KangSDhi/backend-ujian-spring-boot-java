package dev.kangsdhi.backendujianspringbootjava.database.seeder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kangsdhi.backendujianspringbootjava.dto.data.JawabanDto;
import dev.kangsdhi.backendujianspringbootjava.dto.seeder.UjianBaruSeeder;
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
                            StatusUjian.PROSES,
                            "Sigit Boworaharjo"),
                    new UjianBaruSeeder(
                            "Matematika X",
                            convertUtils.convertStringToDatetimeOrTime("05-07-2024 07:00:00"),
                            StatusUjian.PROSES,
                            "Dhini Aprilia Budiarti")
            ));

            for (UjianBaruSeeder ujianBaruSeederItem : dataUjianBaruSeeder) {
                Pengguna pengguna = penggunaRepository.findByNamaPenggunaAndRolePengguna(ujianBaruSeederItem.getNamaPengguna(), RolePengguna.SISWA);
                List<JawabanDto> listJawabanDto = new ArrayList<>();
                Soal soal = soalRepository.findByNamaSoal(ujianBaruSeederItem.getNamaSoal());
                List<BankSoal> listBankSoal = bankSoalRepository.findBySoal(soal);
                for (BankSoal bankSoalItem : listBankSoal) {

                    JawabanDto jawabanDto = new JawabanDto();
                    jawabanDto.setIdBank(bankSoalItem.getId());
                    jawabanDto.setPertanyaan(bankSoalItem.getPertanyaanBankSoal());
                    jawabanDto.setStatusPertanyaan(StatusPertanyaan.BELUM_DIJAWAB);

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

                    jawabanDto.setPilihanA(listPilihan.getFirst());
                    jawabanDto.setPilihanB(listPilihan.get(1));
                    jawabanDto.setPilihanC(listPilihan.get(2));
                    jawabanDto.setPilihanD(listPilihan.get(3));
                    jawabanDto.setPilihanE(listPilihan.get(4));

                    listJawabanDto.add(jawabanDto);
                }

                Collections.shuffle(listJawabanDto);

                System.out.println(Arrays.toString(listJawabanDto.toArray()));
                String listJawabanJson = convertListJawabanToJson(listJawabanDto);

                System.out.println(listJawabanJson);
                Ujian ujian = new Ujian();
                ujian.setSoal(soal);
                ujian.setListJawabanUjian(listJawabanJson);
                ujian.setStatusUjian(StatusUjian.PROSES);
                ujian.setPengguna(pengguna);
                ujian.setWaktuSelesaiUjian(ujianBaruSeederItem.getWaktuSelesaiUjian());
                ujianRepository.save(ujian);
                System.out.println("Membuat Data Ujian Baru : "+pengguna.getNamaPengguna()+" "+ujian.getSoal().getNamaSoal()+" ✅");
            }
        }
    }

    private String convertListJawabanToJson(List<JawabanDto> jawabanDto){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(jawabanDto);
        } catch (JsonProcessingException e){
            logger.error(e.getMessage());
            return null;
        }
    }
}
