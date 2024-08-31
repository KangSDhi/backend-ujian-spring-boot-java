package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kangsdhi.backendujianspringbootjava.dto.data.MataUjianDto;
import dev.kangsdhi.backendujianspringbootjava.dto.data.UjianMappingDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.JawabanUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.MataUjianRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.*;
import dev.kangsdhi.backendujianspringbootjava.enums.AcakSoal;
import dev.kangsdhi.backendujianspringbootjava.enums.StatusMataUjian;
import dev.kangsdhi.backendujianspringbootjava.enums.StatusPertanyaan;
import dev.kangsdhi.backendujianspringbootjava.enums.StatusUjian;
import dev.kangsdhi.backendujianspringbootjava.repository.*;
import dev.kangsdhi.backendujianspringbootjava.services.UjianService;
import dev.kangsdhi.backendujianspringbootjava.services.UserService;
import dev.kangsdhi.backendujianspringbootjava.utils.ConvertUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UjianServiceImplementation implements UjianService {

    private final UserService userService;
    private final UjianRepository ujianRepository;
    private final PenggunaRepository penggunaRepository;
    private final TingkatRepository tingkatRepository;
    private final JurusanRepository jurusanRepository;
    private final SoalRepository soalRepository;
    private final BankSoalRepository bankSoalRepository;

    @Override
    public ResponseWithMessageAndData<Object> listMataUjian(MataUjianRequest mataUjianRequest) {
        Tingkat tingkat = tingkatRepository.findTingkatByTingkat(mataUjianRequest.getTingkat());
        Jurusan jurusan = jurusanRepository.findJurusanByJurusan(mataUjianRequest.getJurusan());
        List<Soal> soalList = soalRepository.findSoalForSiswa(getCurrentDate(), tingkat, jurusan);

        if (soalList.isEmpty()) {
            return createResponseWithMessageAndData(HttpStatus.NOT_FOUND, "Maaf Daftar Ujian Tidak Ditemukan!", null);
        }
        List<MataUjianDto> mataUjianDtoList = soalList.stream()
                .map(this::mapToMataUjianDto)
                .collect(Collectors.toList());

        return createResponseWithMessageAndData(HttpStatus.OK, "Berhasil Mengambil Daftar Ujian!", mataUjianDtoList);
    }

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
        Soal soal = getSoalById(idSoal);

        if (soal == null || isUjianExist(soal)) {
            return createResponseWithMessage(HttpStatus.BAD_REQUEST, "Soal Tidak Ada atau Ujian Sudah Ada!");
        }

        List<BankSoal> bankSoalList = bankSoalRepository.findBySoal(soal);
        if (bankSoalList.isEmpty() || bankSoalList.size() < soal.getButirSoal()) {
            return createResponseWithMessage(HttpStatus.BAD_REQUEST, "Bank Soal Tidak Memadai!");
        }

        List<BankSoal> bankSoalListLimit = bankSoalList.stream().limit(soal.getButirSoal()).toList();

        List<UjianMappingDto> ujianMappingDtoList = prepareUjianMapping(soal, bankSoalListLimit);

        Ujian ujianGenerate = createUjian(soal, getCurrentPengguna(), ujianMappingDtoList);
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
        } catch (Exception e) {
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
        if (soal == null) {
            return createResponseWithMessage(HttpStatus.NOT_FOUND, "Soal Tidak Ditemukan!");
        }
        Ujian ujian = ujianRepository.findBySoalAndPengguna(soal, pengguna).orElse(null);
        if (ujian == null) {
            return createResponseWithMessage(HttpStatus.NOT_FOUND, "Ujian Tidak Ditemukan!");
        }
        ObjectMapper objectMapper = new ObjectMapper();
        List<UjianMappingDto> ujianMappingDtoList;
        try {
            ujianMappingDtoList = Arrays.asList(objectMapper.readValue(ujian.getListJawabanUjian(), UjianMappingDto[].class));
        } catch (Exception e) {
            return createResponseWithMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
        String idBank = jawabanUjianRequest.getIdBank();
        for (UjianMappingDto dto : ujianMappingDtoList) {
            if (dto.getIdBank().equals(UUID.fromString(idBank))) {
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

    private List<UjianMappingDto> generateJawabanDtoList(Soal soal, List<BankSoal> bankSoalList) {
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

    private List<UjianMappingDto> prepareUjianMapping(Soal soal, List<BankSoal> bankSoalList) {
        List<UjianMappingDto> ujianMappingDtoList = generateJawabanDtoList(soal, bankSoalList);
        if (soal.getAcakSoal() == AcakSoal.ACAK) {
            Collections.shuffle(ujianMappingDtoList);
        }
        return ujianMappingDtoList;
    }

    private Soal getSoalById(String idSoal) {
        return soalRepository.findById(UUID.fromString(idSoal)).orElse(null);
    }

    private boolean isUjianExist(Soal soal) {
        return ujianRepository.findBySoalAndPengguna(soal, getCurrentPengguna()).isPresent();
    }

    private Ujian createUjian(Soal soal, Pengguna pengguna, List<UjianMappingDto> ujianMappingDtoList) {
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

    private Pengguna getCurrentPengguna() {
        return penggunaRepository.findByNamaPengguna(userService.getCurrentUser().getUsername()).orElse(null);
    }

    private String convertListJawabanUjianToJsonString(List<UjianMappingDto> ujianMappingDtoList) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(ujianMappingDtoList);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private String serializeJson(Object data) {
        try {
            return new ObjectMapper().writeValueAsString(data);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private <T> T deserializeJson(String json, Class<T> type) {
        try {
            return new ObjectMapper().readValue(json, type);
        } catch (JsonProcessingException e) {
            return null;
        }
    }

    private String getCurrentDate() {
        return ZonedDateTime.now(ZoneId.of("Asia/Jakarta"))
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    private MataUjianDto mapToMataUjianDto(Soal soal) {
        ConvertUtils convertUtils = new ConvertUtils();
        MataUjianDto dto = new MataUjianDto();
        dto.setIdSoal(soal.getId().toString());
        dto.setNamaSoal(soal.getNamaSoal());
        dto.setButirSoal(soal.getButirSoal());
        dto.setAcakSoal(soal.getAcakSoal());
        dto.setWaktuMulaiSoal(convertUtils.convertDateToDatetimeStringFormat(soal.getWaktuMulaiSoal()));
        dto.setWaktuSelesaiSoal(convertUtils.convertDateToDatetimeStringFormat(soal.getWaktuSelesaiSoal()));

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
        long currentMillis = now.toInstant().toEpochMilli();
        long startMillis = soal.getWaktuMulaiSoal().getTime();
        long endMillis = soal.getWaktuSelesaiSoal().getTime();

        if (currentMillis >= startMillis && currentMillis <= endMillis) {
            dto.setStatusMataUjian(StatusMataUjian.MULAI);
        } else if (currentMillis >= startMillis) {
            dto.setStatusMataUjian(StatusMataUjian.SELESAI);
        } else {
            dto.setStatusMataUjian(StatusMataUjian.BELUM_MULAI);
        }

        return dto;
    }

    private ResponseWithMessage createResponseWithMessage(HttpStatus httpStatus, String message) {
        ResponseWithMessage responseWithMessage = new ResponseWithMessage();
        responseWithMessage.setHttpCode(httpStatus.value());
        responseWithMessage.setMessage(message);
        return responseWithMessage;
    }

    private ResponseWithMessageAndData<Object> createResponseWithMessageAndData(HttpStatus httpStatus, String message, Object data) {
        ResponseWithMessageAndData<Object> responseWithMessageAndData = new ResponseWithMessageAndData<>();
        responseWithMessageAndData.setHttpCode(httpStatus.value());
        responseWithMessageAndData.setMessage(message);
        responseWithMessageAndData.setData(data);
        return responseWithMessageAndData;
    }
}
