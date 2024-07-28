package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kangsdhi.backendujianspringbootjava.dto.data.JawabanDto;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
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
            return createResponse(HttpStatus.BAD_REQUEST, "Soal Tidak Ada, Gagal Generate Ujian!");
        }

        Ujian ujian = ujianRepository.findBySoalAndPengguna(soal, pengguna).orElse(null);
        if (ujian != null) {
            return createResponse(HttpStatus.BAD_REQUEST, "Ujian Sudah Ada, Gagal Generate Ujian!");
        }

        List<BankSoal> bankSoalList = bankSoalRepository.findBySoal(soal);
        if (bankSoalList.isEmpty()){
            return createResponse(HttpStatus.BAD_REQUEST, "Bank Soal Kosong, Gagal Generate Ujian!");
        }

        if (bankSoalList.size() < soal.getButirSoal()){
            return createResponse(HttpStatus.BAD_REQUEST, "Butir Soal Melebihi Banyak Bank Soal, Gagal Generate Ujian!");
        }

        List<JawabanDto> jawabanDtoList = generateJawabanDtoList(soal, bankSoalList);
        if (soal.getAcakSoal() == AcakSoal.ACAK){
            Collections.shuffle(jawabanDtoList);
        }

        Ujian ujianGenerate = createUjian(soal, pengguna, jawabanDtoList);
        ujianRepository.save(ujianGenerate);

        String message = soal.getAcakSoal() == AcakSoal.ACAK ? "Ujian Berhasil Dibuat - Acak!" : "Ujian Berhasil Dibuat - Tidak Acak!";
        return createResponse(HttpStatus.CREATED, message);
    }

    private List<JawabanDto> generateJawabanDtoList(Soal soal, List<BankSoal> bankSoalList){
        return bankSoalList.stream().map(bankSoalItem -> {
            JawabanDto jawabanDto = new JawabanDto();
            jawabanDto.setIdBank(bankSoalItem.getId());
            jawabanDto.setPertanyaan(bankSoalItem.getPertanyaanBankSoal());
            jawabanDto.setStatusPertanyaan(StatusPertanyaan.BELUM_DIJAWAB);

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

            jawabanDto.setPilihanA(listPilihan.getFirst());
            jawabanDto.setPilihanB(listPilihan.get(1));
            jawabanDto.setPilihanC(listPilihan.get(2));
            jawabanDto.setPilihanD(listPilihan.get(3));
            jawabanDto.setPilihanE(listPilihan.getLast());

            return jawabanDto;
        }).collect(Collectors.toList());
    }

    private Ujian createUjian(Soal soal, Pengguna pengguna, List<JawabanDto> jawabanDtoList){
        String listJawabanJson = convertListJawabanToJson(jawabanDtoList);

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

    private String convertListJawabanToJson(List<JawabanDto> jawabanDto){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(jawabanDto);
        } catch (JsonProcessingException e){
            return null;
        }
    }

    private ResponseWithMessage createResponse(HttpStatus httpStatus, String message) {
        ResponseWithMessage responseWithMessage = new ResponseWithMessage();
        responseWithMessage.setHttpCode(httpStatus.value());
        responseWithMessage.setMessage(message);
        return responseWithMessage;
    }
}
