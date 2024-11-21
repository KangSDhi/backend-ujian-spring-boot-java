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
        Soal soal = getSoalById(idSoal);
        Ujian ujian = ujianRepository.findBySoalAndPengguna(soal, getCurrentPengguna()).orElse(null);

        ResponseWithMessage responseWithMessage = new ResponseWithMessage();
        String message = (ujian == null) ? "Ujian Tidak Ditemukan!" : "Ujian Ada!";
        responseWithMessage.setMessage(message);
        return responseWithMessage;
    }

    @Override
    public ResponseWithMessage generateUjian(String idSoal) {
        Soal soal = getSoalById(idSoal);

        if (soal == null) {
            return createResponseWithMessage(HttpStatus.BAD_REQUEST, "Soal Tidak Ada!");
        }

        if (isUjianExist(soal)) {
            return createResponseWithMessage(HttpStatus.BAD_REQUEST, "Ujian Sudah Ada!");
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
        Soal soal = getSoalById(idSoal);
        if (soal == null) {
            return createResponseWithMessageAndData(HttpStatus.NOT_FOUND, "Soal Tidak Ditemukan!", null);
        }

        Ujian ujian = ujianRepository.findBySoalAndPengguna(soal, getCurrentPengguna()).orElse(null);
        if (ujian == null) {
            return createResponseWithMessageAndData(HttpStatus.NOT_FOUND, "Ujian Tidak Ditemukan!", null);
        }

        try {
            List<UjianMappingDto> ujianMappingDtoList = List.of(Objects.requireNonNull(deserializeJson(ujian.getListJawabanUjian(), UjianMappingDto[].class)));
            Map<String, Object> dataUjian = createDataUjianMap(ujian, ujianMappingDtoList);
            return createResponseWithMessageAndData(HttpStatus.OK, "Data Ujian Ada", dataUjian);
        } catch (Exception e) {
            return createResponseWithMessageAndData(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), null);
        }
    }

    @Override
    public ResponseWithMessage jawabUjian(JawabanUjianRequest jawabanUjianRequest) {
        System.out.println(jawabanUjianRequest.toString());
        Soal soal = getSoalById(jawabanUjianRequest.getId_soal());
        if (soal == null) {
            return createResponseWithMessage(HttpStatus.NOT_FOUND, "Soal Tidak Ditemukan!");
        }
        Ujian ujian = ujianRepository.findBySoalAndPengguna(soal, getCurrentPengguna()).orElse(null);
        if (ujian == null) {
            return createResponseWithMessage(HttpStatus.NOT_FOUND, "Ujian Tidak Ditemukan!");
        }

        try {
            List<UjianMappingDto> ujianMappingDtoList = updateJawaban(ujian, jawabanUjianRequest);
            ujian.setListJawabanUjian(serializeJson(ujianMappingDtoList));
            ujianRepository.save(ujian);
            return createResponseWithMessage(HttpStatus.CREATED, "Berhasil Memperbarui Jawaban");
        } catch (Exception e) {
            return createResponseWithMessage(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    private List<UjianMappingDto> generateJawabanDtoList(Soal soal, List<BankSoal> bankSoalList) {
        return bankSoalList.stream().map(bankSoalItem -> {
            UjianMappingDto ujianMappingDto = new UjianMappingDto();
            ujianMappingDto.setId_bank(bankSoalItem.getId());
            ujianMappingDto.setPertanyaan(bankSoalItem.getPertanyaanBankSoal());
            ujianMappingDto.setGambar_pertanyaan(bankSoalItem.getGambarPertanyaanBankSoal());
            ujianMappingDto.setStatus_pertanyaan(StatusPertanyaan.BELUM_DIJAWAB);

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

            ujianMappingDto.setPilihan_a(listPilihan.getFirst());
            ujianMappingDto.setPilihan_b(listPilihan.get(1));
            ujianMappingDto.setPilihan_c(listPilihan.get(2));
            ujianMappingDto.setPilihan_d(listPilihan.get(3));
            ujianMappingDto.setPilihan_e(listPilihan.getLast());

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
        String listJawabanJson = serializeJson(ujianMappingDtoList);

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
        dto.setId(soal.getId().toString());
        dto.setNama_soal(soal.getNamaSoal());
        dto.setButir_soal(soal.getButirSoal());
        dto.setAcak_soal(soal.getAcakSoal());
        dto.setWaktu_mulai_soal(convertUtils.convertDateToDatetimeStringFormat(soal.getWaktuMulaiSoal()));
        dto.setWaktu_selesai_soal(convertUtils.convertDateToDatetimeStringFormat(soal.getWaktuSelesaiSoal()));

        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Asia/Jakarta"));
        long currentMillis = now.toInstant().toEpochMilli();
        long startMillis = soal.getWaktuMulaiSoal().getTime();
        long endMillis = soal.getWaktuSelesaiSoal().getTime();

        if (currentMillis >= startMillis && currentMillis <= endMillis) {
            dto.setStatus_mata_ujian(StatusMataUjian.MULAI);
        } else if (currentMillis >= startMillis) {
            dto.setStatus_mata_ujian(StatusMataUjian.SELESAI);
        } else {
            dto.setStatus_mata_ujian(StatusMataUjian.BELUM_MULAI);
        }

        return dto;
    }

    private Map<String, Object> createDataUjianMap(Ujian ujian, List<UjianMappingDto> ujianMappingDtoList) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.MINUTE, 5);

        ConvertUtils convertUtils = new ConvertUtils();
        String waktuAktifSelesaiUjian = convertUtils.convertDateToDatetimeStringFormat(calendar.getTime());
        String waktuSelesaiUjian = convertUtils.convertDateToDatetimeStringFormat(ujian.getWaktuSelesaiUjian());

        Map<String, Object> dataUjianMap = new HashMap<>();
        dataUjianMap.put("soal", ujianMappingDtoList);
        dataUjianMap.put("statusUjian", ujian.getStatusUjian().name());
        dataUjianMap.put("waktuAktifSelesaiUjian", waktuAktifSelesaiUjian);
        dataUjianMap.put("waktuSelesaiUjian", waktuSelesaiUjian);
        return dataUjianMap;
    }

    private List<UjianMappingDto> updateJawaban(Ujian ujian, JawabanUjianRequest jawabanUjianRequest) {
        List<UjianMappingDto> ujianMappingDtoList = Arrays.asList(
                Objects.requireNonNull(deserializeJson(ujian.getListJawabanUjian(), UjianMappingDto[].class))
        );

        UUID idBank = UUID.fromString(jawabanUjianRequest.getId_bank());

//        for (UjianMappingDto dto : ujianMappingDtoList) {
//            if (dto.getIdBank().equals(UUID.fromString(jawabanUjianRequest.getIdBank()))) {
//                dto.setJawaban(jawabanUjianRequest.getJawaban());
//                dto.setStatusPertanyaan(jawabanUjianRequest.getStatusPertanyaan());
//                break;
//            }
//        }

        ujianMappingDtoList.stream()
                .filter(dto -> dto.getId_bank().equals(idBank))
                .findFirst()
                .ifPresent(ujianMappingDto -> {
                    ujianMappingDto.setJawaban(jawabanUjianRequest.getJawaban());
                    ujianMappingDto.setStatus_pertanyaan(jawabanUjianRequest.getStatus_pertanyaan());
                });

        return ujianMappingDtoList;
    }

    private ResponseWithMessage createResponseWithMessage(HttpStatus httpStatus, String message) {
        ResponseWithMessage responseWithMessage = new ResponseWithMessage();
        responseWithMessage.setHttp_code(httpStatus.value());
        responseWithMessage.setMessage(message);
        return responseWithMessage;
    }

    private ResponseWithMessageAndData<Object> createResponseWithMessageAndData(HttpStatus httpStatus, String message, Object data) {
        ResponseWithMessageAndData<Object> responseWithMessageAndData = new ResponseWithMessageAndData<>();
        responseWithMessageAndData.setHttp_code(httpStatus.value());
        responseWithMessageAndData.setMessage(message);
        responseWithMessageAndData.setData(data);
        return responseWithMessageAndData;
    }
}
