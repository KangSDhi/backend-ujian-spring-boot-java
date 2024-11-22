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
                            "5e775007-5a3b-4aea-9f5c-1d93362dfe85.jpg",
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
                            "8eaf1f2f-a112-4a14-9a06-1c69ef36136b.jpg",
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
                            "2146dcbb-56fb-45c9-8161-2a9954364e7c.jpeg",
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
                            "2146dcbb-56fb-45c9-8161-2a9954364e7c.jpeg",
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
                            "3d07c629-0a31-4d9b-b86a-9b4d932cc8cc.jpg",
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
                            "04ba923e-5145-49ee-a562-9e3ad9ac0a1f.jpeg",
                            "6bedd0a8-5047-4710-8b72-44cb529e5fc1.jpeg",
                            "9a65b541-554b-4cd7-8db4-5825f154a4a6.jpg",
                            "c60d4d6a-160d-4698-b7bb-9dae3bfecf7d.jpg",
                            "966b0ea0-0362-4c9c-9795-809e8d0b24e9.jpg",
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
