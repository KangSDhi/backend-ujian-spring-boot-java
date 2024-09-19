package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SoalDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SoalRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Jurusan;
import dev.kangsdhi.backendujianspringbootjava.entities.Soal;
import dev.kangsdhi.backendujianspringbootjava.entities.Tingkat;
import dev.kangsdhi.backendujianspringbootjava.repository.JurusanRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.SoalRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.TingkatRepository;
import dev.kangsdhi.backendujianspringbootjava.services.SoalService;
import dev.kangsdhi.backendujianspringbootjava.utils.ConvertUtils;
import dev.kangsdhi.backendujianspringbootjava.utils.GenerateUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SoalServiceImplementation implements SoalService {

    private final TingkatRepository tingkatRepository;
    private final JurusanRepository jurusanRepository;
    private final SoalRepository soalRepository;

    @Override
    public ResponseWithMessage checkTokenSoal(String idSoal, String token) {
        UUID uuid = UUID.fromString(idSoal);
        Soal soal = soalRepository.findById(uuid).orElse(null);
        ResponseWithMessage responseWithMessage = new ResponseWithMessage();
        if (soal == null) {
            responseWithMessage.setHttpCode(HttpStatus.NOT_FOUND.value());
            responseWithMessage.setMessage("Soal Tidak Ditemukan");
        } else {
            if (!soal.getTokenSoal().equals(token)) {
                responseWithMessage.setHttpCode(HttpStatus.BAD_REQUEST.value());
                responseWithMessage.setMessage("Token Salah!");
            } else {
                responseWithMessage.setHttpCode(HttpStatus.OK.value());
                responseWithMessage.setMessage("Token Benar!");
            }
        }

        return responseWithMessage;
    }

    @Override
    public ResponseWithMessageAndData<List<SoalDto>> listAllSoal() {
        List<Soal> soalList = soalRepository.findAll(Sort.by(Sort.Direction.DESC, "waktuMulaiSoal")
                .and(Sort.by(Sort.Direction.ASC, "tingkat.tingkat"))
                .and(Sort.by(Sort.Direction.ASC, "jurusan.jurusan")));

        List<SoalDto> soalDtoList = soalList.stream().map(this::convertSoalToDto).collect(Collectors.toList());

        ResponseWithMessageAndData<List<SoalDto>> responseWithMessageAndData = new ResponseWithMessageAndData<>();
        responseWithMessageAndData.setHttpCode(HttpStatus.OK.value());
        responseWithMessageAndData.setMessage("Berhasil Mengambil Data Soal!");
        responseWithMessageAndData.setData(soalDtoList);
        return responseWithMessageAndData;
    }

    @Override
    public ResponseWithMessageAndData<SoalDto> soalById(String idSoal) {

        UUID idFromRequest = UUID.fromString(idSoal);
        Soal soal = soalRepository.findById(idFromRequest).orElseThrow(() -> new EntityNotFoundException("Id Soal "+idSoal+" Tidak Ditemukan"));

        SoalDto soalDtoFindById = convertSoalToDto(soal);
        ResponseWithMessageAndData<SoalDto> responseSoal = new ResponseWithMessageAndData<>();
        responseSoal.setMessage("Berhasil Mengambil Data Soal!");
        responseSoal.setHttpCode(HttpStatus.OK.value());
        responseSoal.setData(soalDtoFindById);
        return responseSoal;
    }

    @Override
    public ResponseWithMessageAndData<SoalDto> createSoal(SoalRequest soalRequest) {

        Tingkat tingkat = Optional.ofNullable(tingkatRepository.findTingkatByTingkat(soalRequest.getTingkatSoal()))
                .orElseThrow(() -> new EntityNotFoundException("Tingkat tidak Ditemukan"));

        Jurusan jurusan = Optional.ofNullable(soalRequest.getJurusanSoal())
                .map(jurusanRepository::findJurusanByJurusan)
                .orElse(null);

        Soal newSoal = prepareSoalEntity(new Soal(), soalRequest, tingkat, jurusan);
        Soal soalStore = soalRepository.save(newSoal);

        SoalDto soalDtoCreate = convertSoalToDto(soalStore);
        ResponseWithMessageAndData<SoalDto> responseSoal = new ResponseWithMessageAndData<>();
        responseSoal.setHttpCode(HttpStatus.CREATED.value());
        responseSoal.setMessage("Berhasil Membuat Soal");
        responseSoal.setData(soalDtoCreate);

        return responseSoal;
    }

    @Override
    public ResponseWithMessageAndData<SoalDto> updateSoal(String idSoal, SoalRequest soalRequest) {
        Tingkat tingkat = Optional.ofNullable(tingkatRepository.findTingkatByTingkat(soalRequest.getTingkatSoal()))
                .orElseThrow(() -> new EntityNotFoundException("Tingkat tidak Ditemukan"));

        Jurusan jurusan = Optional.ofNullable(soalRequest.getJurusanSoal())
                .map(jurusanRepository::findJurusanByJurusan)
                .orElse(null);

        UUID soalId = UUID.fromString(idSoal);
        Soal findSoal = soalRepository.findById(soalId).orElseThrow(() -> new EntityNotFoundException("Soal "+soalId+" tidak Ditemukan!"));
        Soal editSoal = prepareSoalEntity(findSoal, soalRequest, tingkat, jurusan);

        Soal soalUpdate = soalRepository.save(editSoal);
        SoalDto soalDtoUpdate = convertSoalToDto(soalUpdate);

        ResponseWithMessageAndData<SoalDto> responseSoal = new ResponseWithMessageAndData<>();
        responseSoal.setHttpCode(HttpStatus.OK.value());
        responseSoal.setMessage("Berhasil Memperbarui Soal");
        responseSoal.setData(soalDtoUpdate);
        return responseSoal;
    }

    @Override
    public ResponseWithMessage deleteSoal(String idSoal) {
        UUID soalId = UUID.fromString(idSoal);
        Soal soal = soalRepository.findById(soalId).orElseThrow(() -> new EntityNotFoundException("Soal "+idSoal+" tidak Ditemukan!"));
        soalRepository.delete(soal);

        ResponseWithMessage responseWithMessage = new ResponseWithMessage();
        responseWithMessage.setHttpCode(HttpStatus.OK.value());
        responseWithMessage.setMessage("Berhasil Menghapus Soal");
        return responseWithMessage;
    }

    private Soal prepareSoalEntity(Soal soal, SoalRequest soalRequest, Tingkat tingkat, Jurusan jurusan) {
        ConvertUtils convertUtils = new ConvertUtils();
        GenerateUtils generateUtils = new GenerateUtils();
        soal.setNamaSoal(soalRequest.getNamaSoal());
        soal.setTokenSoal(generateUtils.generatedSixDigitRandomStringNumeric());
        soal.setTingkat(tingkat);
        soal.setJurusan(jurusan);
        soal.setAcakSoal(soalRequest.getAcakSoal());
        soal.setTipeSoal(soalRequest.getTipeSoal());
        soal.setButirSoal(soalRequest.getButirSoal());
        soal.setDurasiSoal(convertUtils.convertStringToDatetimeOrTime(soalRequest.getDurasiSoal()));
        soal.setWaktuMulaiSoal(convertUtils.convertStringToDatetimeOrTime(soalRequest.getWaktuMulaiSoal()));
        soal.setWaktuSelesaiSoal(convertUtils.convertStringToDatetimeOrTime(soalRequest.getWaktuSelesaiSoal()));
        if (soal.getWaktuMulaiSoal().getTime() > soal.getWaktuSelesaiSoal().getTime()){
            throw new IllegalArgumentException("Waktu mulai lebih besar daripada waktu selesai!");
        }
        return soal;
    }

    private SoalDto convertSoalToDto(Soal soal) {
        ConvertUtils convertUtils = new ConvertUtils();
        SoalDto soalDto = new SoalDto();
        soalDto.setId(soal.getId().toString());
        soalDto.setNamaSoal(soal.getNamaSoal());
        soalDto.setTingkat(soal.getTingkat().getTingkat());
        soalDto.setJurusan(soal.getJurusan() != null ? soal.getJurusan().getJurusan() : null);
        soalDto.setAcakSoal(soal.getAcakSoal());
        soalDto.setTipeSoal(soal.getTipeSoal());
        soalDto.setButirSoal(soal.getButirSoal());
        soalDto.setDurasiSoal(convertUtils.convertDateToTimeStringFormat(soal.getDurasiSoal()));
        soalDto.setWaktuMulaiSoal(convertUtils.convertDateToDatetimeStringFormat(soal.getWaktuMulaiSoal()));
        soalDto.setWaktuSelesaiSoal(convertUtils.convertDateToDatetimeStringFormat(soal.getWaktuSelesaiSoal()));
        return soalDto;
    }

}
