package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.data.MataUjianDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.MataUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import dev.kangsdhi.backendujianspringbootjava.entities.Soal;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import dev.kangsdhi.backendujianspringbootjava.enums.StatusMataUjian;
import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.SoalRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import dev.kangsdhi.backendujianspringbootjava.services.MataUjianService;
import lombok.RequiredArgsConstructor;
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
    public ResponseWithMessageAndData<List<MataUjianDto>> listMataUjian(MataUjianRequest mataUjianRequest) {
        ZonedDateTime jakartaTime = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Tingkat tingkat = tingkatRepository.findTingkatByTingkat(mataUjianRequest.getTingkat());
        Jurusan jurusan = jurusanRepository.findJurusanByJurusan(mataUjianRequest.getJurusan());
        List<Soal> soal = soalRepository.findSoalForSiswa(jakartaTime.format(formatter), tingkat, jurusan);

        ResponseWithMessageAndData<List<MataUjianDto>> mataUjianResponse = new ResponseWithMessageAndData<>();
        List<MataUjianDto> mataUjianDtoList = new ArrayList<>();
        for (Soal soalItem: soal){

            SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

            MataUjianDto mataUjianDto = new MataUjianDto();
            mataUjianDto.setIdSoal(soalItem.getId().toString());
            mataUjianDto.setNamaSoal(soalItem.getNamaSoal());
            mataUjianDto.setButirSoal(soalItem.getButirSoal());
            mataUjianDto.setAcakSoal(soalItem.getAcakSoal());
            mataUjianDto.setWaktuMulaiSoal(dateFormat.format(soalItem.getWaktuMulaiSoal()));
            mataUjianDto.setWaktuSelesaiSoal(dateFormat.format(soalItem.getWaktuSelesaiSoal()));

            long jakartaTimeInTimeMilis = jakartaTime.toInstant().toEpochMilli();
            long waktuMulaiInTimeMilis = soalItem.getWaktuMulaiSoal().getTime();
            long waktuSelesaiInTimeMilis = soalItem.getWaktuSelesaiSoal().getTime();
            if (jakartaTimeInTimeMilis >= waktuMulaiInTimeMilis && jakartaTimeInTimeMilis <= waktuSelesaiInTimeMilis) {
                mataUjianDto.setStatusMataUjian(StatusMataUjian.MULAI);
            } else if (jakartaTimeInTimeMilis >= waktuMulaiInTimeMilis) {
                mataUjianDto.setStatusMataUjian(StatusMataUjian.SELESAI);
            } else {
                mataUjianDto.setStatusMataUjian(StatusMataUjian.BELUM_MULAI);
            }

            mataUjianDtoList.add(mataUjianDto);
        }
        mataUjianResponse.setData(mataUjianDtoList);
        return mataUjianResponse;
    }
}
