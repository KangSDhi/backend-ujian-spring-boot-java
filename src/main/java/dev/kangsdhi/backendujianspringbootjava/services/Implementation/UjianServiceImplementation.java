package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kangsdhi.backendujianspringbootjava.dto.data.UjianMappingDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.JawabanUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.BankSoal;
import dev.kangsdhi.backendujianspringbootjava.entities.Pengguna;
import dev.kangsdhi.backendujianspringbootjava.entities.Soal;
import dev.kangsdhi.backendujianspringbootjava.entities.Ujian;
import dev.kangsdhi.backendujianspringbootjava.enums.AcakSoal;
import dev.kangsdhi.backendujianspringbootjava.enums.StatusPertanyaan;
import dev.kangsdhi.backendujianspringbootjava.enums.StatusUjian;
import dev.kangsdhi.backendujianspringbootjava.repository.BankSoalRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.SoalRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.UjianRepository;
import dev.kangsdhi.backendujianspringbootjava.services.UjianService;
import dev.kangsdhi.backendujianspringbootjava.services.UserService;
import dev.kangsdhi.backendujianspringbootjava.utils.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UjianServiceImplementation implements UjianService {

    private final UserService userService;
    private final UjianRepository ujianRepository;
    private final PenggunaRepository penggunaRepository;
    private final SoalRepository soalRepository;
    private final BankSoalRepository bankSoalRepository;

    @Override
    public ResponseWithMessage checkInUjian(String idSoal) {
        UUID soalId = UUID.fromString(idSoal);
        Pengguna pengguna = getCurrentPengguna();
        Soal soal = soalRepository.findById(soalId).orElse(null);
        Ujian ujian = ujianRepository.findBySoalAndPengguna(soal, pengguna).orElse(null);

        ResponseWithMessage responseWithMessage = new ResponseWithMessage();
        String message = (ujian == null) ? "Ujian Tidak Ditemukan!" : "Ujian Ada!";
        responseWithMessage.setMessage(message);
        return responseWithMessage;
    }

    @Override
    public ResponseWithMessage generateUjian(String idSoal) {
        UUID soalId = UUID.fromString(idSoal);
        Pengguna pengguna = getCurrentPengguna();

        Soal soal = soalRepository.findById(soalId).orElse(null);
        if (soal == null){
            return createResponseWithMessage(HttpStatus.BAD_REQUEST, "Soal Tidak Ada, Gagal Generate Ujian!");
        }

        Ujian ujian = ujianRepository.findBySoalAndPengguna(soal, pengguna).orElse(null);
        if (ujian != null) {
            return createResponseWithMessage(HttpStatus.BAD_REQUEST, "Ujian Sudah Ada, Gagal Generate Ujian!");
        }

        List<BankSoal> bankSoalList = bankSoalRepository.findBySoal(soal);
        if (bankSoalList.isEmpty()){
            return createResponseWithMessage(HttpStatus.BAD_REQUEST, "Bank Soal Kosong, Gagal Generate Ujian!");
        }

        if (bankSoalList.size() < soal.getButirSoal()){
            return createResponseWithMessage(HttpStatus.BAD_REQUEST, "Butir Soal Melebihi Banyak Bank Soal, Gagal Generate Ujian!");
        }

        List<UjianMappingDto> ujianMappingDtoList = generateJawabanDtoList(soal, bankSoalList);
        if (soal.getAcakSoal() == AcakSoal.ACAK){
            Collections.shuffle(ujianMappingDtoList);
        }

        Ujian ujianGenerate = createUjian(soal, pengguna, ujianMappingDtoList);
        ujianRepository.save(ujianGenerate);

        String message = soal.getAcakSoal() == AcakSoal.ACAK ? "Ujian Berhasil Dibuat - Acak!" : "Ujian Berhasil Dibuat - Tidak Acak!";
        return createResponseWithMessage(HttpStatus.CREATED, message);
    }

    @Override
    public ResponseWithMessageAndData<Object> loadDataJawabanSoal(String idSoal) {
        UUID soalId = UUID.fromString(idSoal);
        Pengguna pengguna = getCurrentPengguna();
        Soal soal = soalRepository.findById(soalId).orElse(null);
        if (soal == null){
            return createResponseWithMessageAndData(HttpStatus.NOT_FOUND, "Soal Tidak Ditemukan!", null);
        }
        Ujian ujian = ujianRepository.findBySoalAndPengguna(soal, pengguna).orElse(null);
        if (ujian == null){
            return createResponseWithMessageAndData(HttpStatus.NOT_FOUND, "Ujian Tidak Ditemukan!", null);
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<UjianMappingDto> ujianMappingDtoList;
        try {
            ujianMappingDtoList = Arrays.asList(objectMapper.readValue(ujian.getListJawabanUjian(), UjianMappingDto[].class));
        }catch (Exception e){
            return createResponseWithMessageAndData(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 5);

        ConvertUtils convertUtils = new ConvertUtils();
        String waktuAktifSelesaiUjian = convertUtils.convertDateToDatetimeStringFormat(calendar.getTime());
        String waktuSelesaiUjian = convertUtils.convertDateToDatetimeStringFormat(ujian.getWaktuSelesaiUjian());
        HashMap<String, Object> dataUjian = new HashMap<>();
        dataUjian.put("soal", ujianMappingDtoList);
        dataUjian.put("statusUjian", ujian.getStatusUjian().name());
        dataUjian.put("waktuAktifSelesaiUjian", waktuAktifSelesaiUjian);
        dataUjian.put("waktuSelesaiUjian", waktuSelesaiUjian);
        return createResponseWithMessageAndData(HttpStatus.OK, "Data Ujian Ada", dataUjian);
    }

    @Override
    public ResponseWithMessage jawabUjian(JawabanUjianRequest jawabanUjianRequest) {
        Pengguna pengguna = getCurrentPengguna();
        UUID idSoal = UUID.fromString(jawabanUjianRequest.getIdSoal());
        Soal soal = soalRepository.findById(idSoal).orElse(null);
        if (soal == null){
            return createResponseWithMessage(HttpStatus.NOT_FOUND, "Soal Tidak Ditemukan!");
        }
        Ujian ujian = ujianRepository.findBySoalAndPengguna(soal, pengguna).orElse(null);
        if (ujian == null){
            return createResponseWithMessage(HttpStatus.NOT_FOUND, "Ujian Tidak Ditemukan!");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<UjianMappingDto> ujianMappingDtoList;
        try {
            ujianMappingDtoList = Arrays.asList(objectMapper.readValue(ujian.getListJawabanUjian(), UjianMappingDto[].class));
        }catch (Exception e){
            return createResponseWithMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        String idBank = jawabanUjianRequest.getIdBank();
        for (UjianMappingDto dto : ujianMappingDtoList){
            if (dto.getIdBank().equals(UUID.fromString(idBank))){
                dto.setJawaban(jawabanUjianRequest.getJawaban());
                dto.setStatusPertanyaan(jawabanUjianRequest.getStatusPertanyaan());
                break;
            }
        }
        String listJawabanJson = convertListJawabanUjianToJsonString(ujianMappingDtoList);
        ujian.setListJawabanUjian(listJawabanJson);
        ujianRepository.save(ujian);
        return createResponseWithMessage(HttpStatus.CREATED, "Berhasil Memperbarui Jawaban");
    }

    private List<UjianMappingDto> generateJawabanDtoList(Soal soal, List<BankSoal> bankSoalList){
        return bankSoalList.stream().map(bankSoalItem -> {
            UjianMappingDto ujianMappingDto = new UjianMappingDto();
            ujianMappingDto.setIdBank(bankSoalItem.getId());
            ujianMappingDto.setPertanyaan(bankSoalItem.getPertanyaanBankSoal());
            ujianMappingDto.setGambarPertanyaan(bankSoalItem.getGambarPertanyaanBankSoal());
            ujianMappingDto.setStatusPertanyaan(StatusPertanyaan.BELUM_DIJAWAB);

            List<String> listPilihan = Arrays.asList(
                    bankSoalItem.getPilihanA(),
                    bankSoalItem.getPilihanB(),
                    bankSoalItem.getPilihanC(),
                    bankSoalItem.getPilihanD(),
                    bankSoalItem.getPilihanE()
            );

            if (soal.getAcakSoal() == AcakSoal.ACAK) {
                Collections.shuffle(listPilihan);
            }

            ujianMappingDto.setPilihanA(listPilihan.getFirst());
            ujianMappingDto.setPilihanB(listPilihan.get(1));
            ujianMappingDto.setPilihanC(listPilihan.get(2));
            ujianMappingDto.setPilihanD(listPilihan.get(3));
            ujianMappingDto.setPilihanE(listPilihan.getLast());

            return ujianMappingDto;
        }).collect(Collectors.toList());
    }

    private Ujian createUjian(Soal soal, Pengguna pengguna, List<UjianMappingDto> ujianMappingDtoList){
        String listJawabanJson = convertListJawabanUjianToJsonString(ujianMappingDtoList);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(soal.getDurasiSoal());

        Calendar waktuMulaiCalendar = Calendar.getInstance();
        waktuMulaiCalendar.setTime(soal.getWaktuMulaiSoal());

        waktuMulaiCalendar.add(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY));
        waktuMulaiCalendar.add(Calendar.MINUTE, calendar.get(Calendar.MINUTE));
        waktuMulaiCalendar.add(Calendar.SECOND, calendar.get(Calendar.SECOND));

        Date waktuSelesaiUjian = waktuMulaiCalendar.getTime();

        Ujian ujian = new Ujian();
        ujian.setSoal(soal);
        ujian.setListJawabanUjian(listJawabanJson);
        ujian.setStatusUjian(StatusUjian.PROSES);
        ujian.setPengguna(pengguna);
        ujian.setWaktuSelesaiUjian(waktuSelesaiUjian);
        return ujian;
    }

    private Pengguna getCurrentPengguna(){
        String namaPengguna = userService.getCurrentUser().getUsername();
        return penggunaRepository.findByNamaPengguna(namaPengguna).orElse(null);
    }

    private String convertListJawabanUjianToJsonString(List<UjianMappingDto> ujianMappingDtoList){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(ujianMappingDtoList);
        } catch (JsonProcessingException e){
            return null;
        }
    }

    private ResponseWithMessage createResponseWithMessage(HttpStatus httpStatus, String message) {
        ResponseWithMessage responseWithMessage = new ResponseWithMessage();
        responseWithMessage.setHttpCode(httpStatus.value());
        responseWithMessage.setMessage(message);
        return responseWithMessage;
    }

    private ResponseWithMessageAndData<Object> createResponseWithMessageAndData(HttpStatus httpStatus, String message, Object data){
        ResponseWithMessageAndData<Object> responseWithMessageAndData = new ResponseWithMessageAndData<>();
        responseWithMessageAndData.setHttpCode(httpStatus.value());
        responseWithMessageAndData.setMessage(message);
        responseWithMessageAndData.setData(data);
        return responseWithMessageAndData;
    }
}
