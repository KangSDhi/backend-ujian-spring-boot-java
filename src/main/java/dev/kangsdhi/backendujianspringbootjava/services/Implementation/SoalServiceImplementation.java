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
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.hibernate.ObjectNotFoundException;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SoalServiceImplementation implements SoalService {

    private final TingkatRepository tingkatRepository;
    private final JurusanRepository jurusanRepository;
    private final SoalRepository soalRepository;

    @Override
    public ResponseWithMessageAndData<List<SoalDto>> listAllSoal() {
        List<Soal> soalList = soalRepository.findAll(Sort.by(Sort.Direction.ASC, "waktuMulaiSoal"));
        ConvertUtils convertUtils = new ConvertUtils();

        List<SoalDto> soalDtoList = soalList.stream().map(soal -> {
            SoalDto soalDto = new SoalDto();
            soalDto.setId(soal.getId().toString());
            soalDto.setNamaSoal(soal.getNamaSoal());
            soalDto.setButirSoal(soal.getButirSoal());
            soalDto.setTipeSoal(soal.getTipeSoal());
            soalDto.setAcakSoal(soal.getAcakSoal());
            soalDto.setTingkat(soal.getTingkat().getTingkat());
            if (soal.getJurusan() != null){
                soalDto.setJurusan(soal.getJurusan().getJurusan());
            }
            soalDto.setDurasiSoal(convertUtils.convertDateToTimeStringFormat(soal.getDurasiSoal()));
            soalDto.setWaktuMulaiSoal(convertUtils.convertDateToDatetimeStringFormat(soal.getWaktuMulaiSoal()));
            soalDto.setWaktuSelesaiSoal(convertUtils.convertDateToDatetimeStringFormat(soal.getWaktuSelesaiSoal()));

            return soalDto;
        }).collect(Collectors.toList());

        ResponseWithMessageAndData<List<SoalDto>> responseWithMessageAndData = new ResponseWithMessageAndData<>();
        responseWithMessageAndData.setHttpCode(HttpStatus.OK.value());
        responseWithMessageAndData.setMessage("Berhasil Mengambil Data Soal!");
        responseWithMessageAndData.setData(soalDtoList);
        return responseWithMessageAndData;
    }

    @Override
    public ResponseWithMessageAndData<SoalDto> soalById(String idSoal) {

        UUID idFromRequest = UUID.fromString(idSoal);

        Soal soal = soalRepository.findById(idFromRequest).orElse(null);

        ResponseWithMessageAndData<SoalDto> responseSoal = new ResponseWithMessageAndData<>();

        if (soal == null){
            responseSoal.setData(null);
            return responseSoal;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        SoalDto soalDto = new SoalDto();
        soalDto.setId(soal.getId().toString());
        soalDto.setNamaSoal(soal.getNamaSoal());
        soalDto.setTingkat(soal.getTingkat().getTingkat());
        soalDto.setJurusan(soal.getJurusan() != null ? soal.getJurusan().getJurusan() : null);
        soalDto.setDurasiSoal(soal.getDurasiSoal().toString());
        soalDto.setButirSoal(soal.getButirSoal());
        soalDto.setAcakSoal(soal.getAcakSoal());
        soalDto.setTipeSoal(soal.getTipeSoal());
        soalDto.setWaktuMulaiSoal(dateFormat.format(soal.getWaktuMulaiSoal()));
        soalDto.setWaktuSelesaiSoal(dateFormat.format(soal.getWaktuSelesaiSoal()));

        responseSoal.setData(soalDto);
        return responseSoal;
    }

    @Override
    public ResponseWithMessageAndData<SoalDto> createSoal(SoalRequest soalRequest) {
        Tingkat tingkat = tingkatRepository.findTingkatByTingkat(soalRequest.getTingkatSoal());
        if (tingkat == null){
            throw new NoSuchElementException("Tingkat "+soalRequest.getTingkatSoal()+" Tidak Ditemukan!");
        }
        
        Jurusan jurusan = null;

        if (soalRequest.getJurusanSoal() != null){
            jurusan = jurusanRepository.findJurusanByJurusan(soalRequest.getJurusanSoal());
            if (jurusan == null){
                throw new NoSuchElementException("Jurusan "+soalRequest.getJurusanSoal()+" Tidak Ditemukan!");
            }
        }

        Soal soal = new Soal();
        soal.setNamaSoal(soalRequest.getNamaSoal());
        soal.setTingkat(tingkat);
        soal.setJurusan(jurusan);
        soal.setAcakSoal(soalRequest.getAcakSoal());
        soal.setTipeSoal(soalRequest.getTipeSoal());
        soal.setButirSoal(soalRequest.getButirSoal());

        ConvertUtils convertUtils = new ConvertUtils();
        soal.setDurasiSoal(convertUtils.convertStringToDatetimeOrTime(soalRequest.getDurasiSoal()));
        soal.setWaktuMulaiSoal(convertUtils.convertStringToDatetimeOrTime(soalRequest.getWaktuMulaiSoal()));
        soal.setWaktuSelesaiSoal(convertUtils.convertStringToDatetimeOrTime(soalRequest.getWaktuSelesaiSoal()));

        if (soal.getWaktuMulaiSoal().getTime() > soal.getWaktuSelesaiSoal().getTime()){
            throw new NoSuchElementException("Waktu mulai lebih besar daripada waktu selesai!");
        }

        Soal soalStore = soalRepository.save(soal);

        SoalDto soalDto = new SoalDto();
        soalDto.setId(soalStore.getId().toString());
        soalDto.setNamaSoal(soalStore.getNamaSoal());
        soalDto.setButirSoal(soalStore.getButirSoal());
        soalDto.setTipeSoal(soalStore.getTipeSoal());
        soalDto.setAcakSoal(soalStore.getAcakSoal());
        soalDto.setTingkat(soalStore.getTingkat().getTingkat());
        if (soalStore.getJurusan() != null){
            soalDto.setJurusan(soalStore.getJurusan().getJurusan());
        }
        soalDto.setDurasiSoal(convertUtils.convertDateToTimeStringFormat(soalStore.getDurasiSoal()));
        soalDto.setWaktuMulaiSoal(convertUtils.convertDateToDatetimeStringFormat(soalStore.getWaktuMulaiSoal()));
        soalDto.setWaktuSelesaiSoal(convertUtils.convertDateToDatetimeStringFormat(soalStore.getWaktuSelesaiSoal()));

        ResponseWithMessageAndData<SoalDto> responseSoal = new ResponseWithMessageAndData<>();
        responseSoal.setHttpCode(HttpStatus.CREATED.value());
        responseSoal.setMessage("Berhasil Membuat Soal");
        responseSoal.setData(soalDto);

        return responseSoal;
    }

    @Override
    public ResponseWithMessageAndData<SoalDto> updateSoal(String idSoal, SoalRequest soalRequest) {
        Tingkat tingkat = tingkatRepository.findTingkatByTingkat(soalRequest.getTingkatSoal());
        if (tingkat == null){
            throw new NoSuchElementException("Tingkat "+soalRequest.getTingkatSoal()+" Tidak Ditemukan!");
        }

        Jurusan jurusan = null;

        if (soalRequest.getJurusanSoal() != null){
            jurusan = jurusanRepository.findJurusanByJurusan(soalRequest.getJurusanSoal());
            if (jurusan == null){
                throw new NoSuchElementException("Jurusan "+soalRequest.getJurusanSoal()+" Tidak Ditemukan!");
            }
        }

        ConvertUtils convertUtils = new ConvertUtils();

        UUID soalId = UUID.fromString(idSoal);
        Soal soal = soalRepository.findById(soalId).orElseThrow(() -> new EntityNotFoundException("Soal "+soalId+" tidak Ditemukan!"));
        soal.setNamaSoal(soalRequest.getNamaSoal());
        soal.setTingkat(tingkat);
        soal.setJurusan(jurusan);
        soal.setAcakSoal(soalRequest.getAcakSoal());
        soal.setTipeSoal(soalRequest.getTipeSoal());
        soal.setButirSoal(soalRequest.getButirSoal());
        soal.setDurasiSoal(convertUtils.convertStringToDatetimeOrTime(soalRequest.getDurasiSoal()));
        soal.setWaktuMulaiSoal(convertUtils.convertStringToDatetimeOrTime(soalRequest.getWaktuMulaiSoal()));
        soal.setWaktuSelesaiSoal(convertUtils.convertStringToDatetimeOrTime(soalRequest.getWaktuSelesaiSoal()));
        Soal soalUpdate = soalRepository.save(soal);
        SoalDto soalDto = new SoalDto();
        soalDto.setId(soalUpdate.getId().toString());
        soalDto.setNamaSoal(soalUpdate.getNamaSoal());
        soalDto.setTingkat(soalUpdate.getTingkat().getTingkat());
        if (soalUpdate.getJurusan() != null){
            soalDto.setJurusan(soalUpdate.getJurusan().getJurusan());
        }
        soalDto.setAcakSoal(soalUpdate.getAcakSoal());
        soalDto.setTipeSoal(soalUpdate.getTipeSoal());
        soalDto.setButirSoal(soalUpdate.getButirSoal());
        soalDto.setDurasiSoal(convertUtils.convertDateToTimeStringFormat(soalUpdate.getDurasiSoal()));
        soalDto.setWaktuMulaiSoal(convertUtils.convertDateToDatetimeStringFormat(soalUpdate.getWaktuMulaiSoal()));
        soalDto.setWaktuSelesaiSoal(convertUtils.convertDateToDatetimeStringFormat(soalUpdate.getWaktuSelesaiSoal()));

        ResponseWithMessageAndData<SoalDto> responseSoal = new ResponseWithMessageAndData<>();
        responseSoal.setHttpCode(HttpStatus.OK.value());
        responseSoal.setMessage("Berhasil Memperbarui Soal");
        responseSoal.setData(soalDto);
        return responseSoal;
    }

    @Override
    public ResponseWithMessage deleteSoal(String idSoal) {
        UUID soalId = UUID.fromString(idSoal);
        soalRepository.deleteById(soalId);

        ResponseWithMessage responseWithMessage = new ResponseWithMessage();
        responseWithMessage.setHttpCode(HttpStatus.OK.value());
        responseWithMessage.setMessage("Berhasil Menghapus Soal");
        return responseWithMessage;
    }

}
