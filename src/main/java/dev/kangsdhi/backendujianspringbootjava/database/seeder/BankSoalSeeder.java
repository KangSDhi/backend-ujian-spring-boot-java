package dev.kangsdhi.backendujianspringbootjava.database.seeder;

import dev.kangsdhi.backendujianspringbootjava.dto.seeder.BankSoalSeederDto;
import dev.kangsdhi.backendujianspringbootjava.entities.BankSoal;
import dev.kangsdhi.backendujianspringbootjava.entities.Soal;
import dev.kangsdhi.backendujianspringbootjava.repository.BankSoalRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.SoalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BankSoalSeeder {

    @Autowired
    private BankSoalRepository bankSoalRepository;

    @Autowired
    private SoalRepository soalRepository;

    public void seedBankSoal() {
        List<BankSoal> dataBankSoal = bankSoalRepository.findAll();
        if (dataBankSoal.isEmpty()){
            List<BankSoalSeederDto> dataBankSoalSeederDtos = new ArrayList<>(List.of(
                    new BankSoalSeederDto(
                            "Matematika X",
                            "5 + 5 =",
                            null,
                            "4",
                            "5",
                            "3",
                            "7",
                            "10",
                            0F,
                            0F,
                            0F,
                            0F,
                            10F
                    ),
                    new BankSoalSeederDto(
                            "Matematika X",
                            "7 / 7 =",
                            null,
                            "4",
                            "5",
                            "3",
                            "1",
                            "10",
                            0F,
                            0F,
                            0F,
                            10F,
                            0F
                    ),
                    new BankSoalSeederDto(
                            "Matematika X",
                            "3 + 7 =",
                            "54b3de0f-69e4-4ea6-aca5-f5cadf5dd631.jpeg",
                            "11",
                            "5",
                            "23",
                            "12",
                            "10",
                            0F,
                            0F,
                            0F,
                            0F,
                            10F
                    ),
                    new BankSoalSeederDto(
                            "Matematika X",
                            "3 - 4 =",
                            null,
                            "-4",
                            "-1",
                            "3",
                            "11",
                            "10",
                            0F,
                            10F,
                            0F,
                            0F,
                            0F
                    ),
                    new BankSoalSeederDto(
                            "Matematika X",
                            "4 - 4 x 4 =",
                            null,
                            "2",
                            "-10",
                            "3",
                            "-12",
                            "10",
                            0F,
                            0F,
                            0F,
                            10F,
                            0F
                    ),
                    new BankSoalSeederDto(
                            "Matematika X",
                            "2 - 4 / 4 =",
                            "54b3de0f-69e4-4ea6-aca5-f5cadf5dd631.jpeg",
                            "2",
                            "-1",
                            "1",
                            "-2",
                            "10",
                            0F,
                            0F,
                            10F,
                            0F,
                            0F
                    ),
                    new BankSoalSeederDto(
                            "Matematika X",
                            "4 - 4 - 4 =",
                            null,
                            "1",
                            "-4",
                            "3",
                            "-5",
                            "10",
                            0F,
                            10F,
                            0F,
                            0F,
                            0F
                    ),
                    new BankSoalSeederDto(
                            "Matematika X",
                            "45 + 5 =",
                            "54b3de0f-69e4-4ea6-aca5-f5cadf5dd631.jpeg",
                            "2",
                            "0",
                            "50",
                            "12",
                            "10",
                            0F,
                            0F,
                            10F,
                            0F,
                            0F
                    ),
                    new BankSoalSeederDto(
                            "Matematika X",
                            "89 - 578 =",
                            null,
                            "1222",
                            "103",
                            "33",
                            "4412",
                            "-489",
                            0F,
                            0F,
                            0F,
                            0F,
                            10F
                    ),
                    new BankSoalSeederDto(
                            "Matematika X",
                            "4 - 70 =",
                            null,
                            "26",
                            "610",
                            "53",
                            "54b3de0f-69e4-4ea6-aca5-f5cadf5dd631.jpeg",
                            "-66",
                            0F,
                            0F,
                            0F,
                            0F,
                            10F
                    ),
                    new BankSoalSeederDto(
                            "Matematika X",
                            "Manakah Gambar PC?",
                            null,
                            "13a5fb23-dc2d-4dea-8b7b-6e48788a5369.jpg",
                            "41eca14b-23d6-4d65-a377-18f8e37a2bf5.jpeg",
                            "52469ebe-6d05-4fbb-88c2-c2497775a1ec.png",
                            "90080107-5303-47c1-b017-39c05b32310c.jpg",
                            "d190d513-3a17-497b-ac0c-bbc4fd9874fd.png",
                            0F,
                            0F,
                            0F,
                            0F,
                            10F
                    )
            ));

            for (BankSoalSeederDto bankSoalSeederDtoItem : dataBankSoalSeederDtos) {
                Soal soal = soalRepository.findByNamaSoal(bankSoalSeederDtoItem.getNamaSoal());

                BankSoal bankSoal = new BankSoal();
                bankSoal.setSoal(soal);
                bankSoal.setPertanyaanBankSoal(bankSoalSeederDtoItem.getPertanyaanBankSoal());
                bankSoal.setGambarPertanyaanBankSoal(bankSoalSeederDtoItem.getGambarPertanyaanBankSoal());
                bankSoal.setPilihanA(bankSoalSeederDtoItem.getPilihanABankSoal());
                bankSoal.setPilihanB(bankSoalSeederDtoItem.getPilihanBBankSoal());
                bankSoal.setPilihanC(bankSoalSeederDtoItem.getPilihanCBankSoal());
                bankSoal.setPilihanD(bankSoalSeederDtoItem.getPilihanDBankSoal());
                bankSoal.setPilihanE(bankSoalSeederDtoItem.getPilihanEBankSoal());
                bankSoal.setNilaiA(bankSoalSeederDtoItem.getNilaiABankSoal());
                bankSoal.setNilaiA(bankSoalSeederDtoItem.getNilaiABankSoal());
                bankSoal.setNilaiB(bankSoalSeederDtoItem.getNilaiBBankSoal());
                bankSoal.setNilaiC(bankSoalSeederDtoItem.getNilaiCBankSoal());
                bankSoal.setNilaiD(bankSoalSeederDtoItem.getNilaiDBankSoal());
                bankSoal.setNilaiE(bankSoalSeederDtoItem.getNilaiEBankSoal());
                bankSoalRepository.save(bankSoal);
                System.out.println("Membuat Data Bank Soal Baru : "+bankSoal.getSoal().getNamaSoal()+" ✅");
            }
        }
    }

}
