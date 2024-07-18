package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.MataUjian;
import dev.kangsdhi.backendujianspringbootjava.dto.MataUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.MataUjianResponse;
import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import dev.kangsdhi.backendujianspringbootjava.entities.Soal;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import dev.kangsdhi.backendujianspringbootjava.enums.StatusMataUjian;
import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.SoalRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import dev.kangsdhi.backendujianspringbootjava.services.MataUjianService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class MataUjianServiceImplementation implements MataUjianService {

    private final TingkatRepository tingkatRepository;
    private final JurusanRepository jurusanRepository;
    private final SoalRepository soalRepository;

    @Override
    public MataUjianResponse<List<MataUjian>> listMataUjian(MataUjianRequest mataUjianRequest) {
        ZonedDateTime jakartaTime = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Tingkat tingkat = tingkatRepository.findTingkatByTingkat(mataUjianRequest.getTingkat());
        Jurusan jurusan = jurusanRepository.findJurusanByJurusan(mataUjianRequest.getJurusan());
        List<Soal> soal = soalRepository.findSoalForSiswa(jakartaTime.format(formatter), tingkat, jurusan);

        MataUjianResponse<List<MataUjian>> mataUjianResponse = new MataUjianResponse<>();
        List<MataUjian> mataUjianList = new ArrayList<>();
        for (Soal soalItem: soal){

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            MataUjian mataUjian = new MataUjian();
            mataUjian.setIdSoal(soalItem.getId().toString());
            mataUjian.setNamaSoal(soalItem.getNamaSoal());
            mataUjian.setButirSoal(soalItem.getButirSoal());
            mataUjian.setAcakSoal(soalItem.getAcakSoal());
            mataUjian.setWaktuMulaiSoal(dateFormat.format(soalItem.getWaktuMulaiSoal()));
            mataUjian.setWaktuSelesaiSoal(dateFormat.format(soalItem.getWaktuSelesaiSoal()));

            long jakartaTimeInTimeMilis = jakartaTime.toInstant().toEpochMilli();
            long waktuMulaiInTimeMilis = soalItem.getWaktuMulaiSoal().getTime();
            long waktuSelesaiInTimeMilis = soalItem.getWaktuSelesaiSoal().getTime();
            if (jakartaTimeInTimeMilis >= waktuMulaiInTimeMilis && jakartaTimeInTimeMilis <= waktuSelesaiInTimeMilis) {
                mataUjian.setStatusMataUjian(StatusMataUjian.MULAI);
            } else if (jakartaTimeInTimeMilis >= waktuMulaiInTimeMilis) {
                mataUjian.setStatusMataUjian(StatusMataUjian.SELESAI);
            } else {
                mataUjian.setStatusMataUjian(StatusMataUjian.BELUM_MULAI);
            }

            mataUjianList.add(mataUjian);
        }
        mataUjianResponse.setData(mataUjianList);
        return mataUjianResponse;
    }
}
