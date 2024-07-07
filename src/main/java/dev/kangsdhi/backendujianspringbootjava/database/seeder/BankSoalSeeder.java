package dev.kangsdhi.backendujianspringbootjava.database.seeder;

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
            List<dev.kangsdhi.backendujianspringbootjava.dto.BankSoalSeeder> dataBankSoalSeeders = new ArrayList<>(List.of(
                    new dev.kangsdhi.backendujianspringbootjava.dto.BankSoalSeeder(
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
                    new dev.kangsdhi.backendujianspringbootjava.dto.BankSoalSeeder(
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
                    )
            ));

            for (dev.kangsdhi.backendujianspringbootjava.dto.BankSoalSeeder bankSoalSeederItem : dataBankSoalSeeders) {
                Soal soal = soalRepository.findByNamaSoal(bankSoalSeederItem.getNamaSoal());

                BankSoal bankSoal = new BankSoal();
                bankSoal.setSoal(soal);
                bankSoal.setPertanyaanBankSoal(bankSoalSeederItem.getPertanyaanBankSoal());
                bankSoal.setGambarPertanyaanBankSoal(bankSoalSeederItem.getGambarPertanyaanBankSoal());
                bankSoal.setPilihanA(bankSoalSeederItem.getPilihanABankSoal());
                bankSoal.setPilihanB(bankSoalSeederItem.getPilihanBBankSoal());
                bankSoal.setPilihanC(bankSoalSeederItem.getPilihanCBankSoal());
                bankSoal.setPilihanD(bankSoalSeederItem.getPilihanDBankSoal());
                bankSoal.setPilihanE(bankSoalSeederItem.getPilihanEBankSoal());
                bankSoal.setNilaiA(bankSoalSeederItem.getNilaiABankSoal());
                bankSoal.setNilaiA(bankSoalSeederItem.getNilaiABankSoal());
                bankSoal.setNilaiB(bankSoalSeederItem.getNilaiBBankSoal());
                bankSoal.setNilaiC(bankSoalSeederItem.getNilaiCBankSoal());
                bankSoal.setNilaiD(bankSoalSeederItem.getNilaiDBankSoal());
                bankSoal.setNilaiE(bankSoalSeederItem.getNilaiEBankSoal());
                bankSoalRepository.save(bankSoal);
                System.out.println("Membuat Data Bank Soal Baru : "+bankSoal.getSoal().getNamaSoal()+" ✅");
            }
        }
    }

}
