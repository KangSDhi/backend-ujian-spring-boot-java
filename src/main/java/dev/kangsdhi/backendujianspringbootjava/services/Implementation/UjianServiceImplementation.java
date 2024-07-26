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
import org.springframework.stereotype.Service;

import java.util.*;

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
        String namaPengguna = userService.getCurrentUser().getUsername();
        Pengguna pengguna = penggunaRepository.findByNamaPengguna(namaPengguna).orElse(null);
        Soal soal = soalRepository.findById(soalId).orElse(null);
        Ujian ujian = ujianRepository.findBySoalAndPengguna(soal, pengguna).orElse(null);
        ResponseWithMessage responseWithMessage = new ResponseWithMessage();
        if (ujian == null) {
            responseWithMessage.setMessage("Ujian Tidak Ditemukan!");
        } else {
            responseWithMessage.setMessage("Ujian Ada!");
        }
        return responseWithMessage;
    }

    @Override
    public ResponseWithMessage generateUjian(String idSoal) {
        UUID soalId = UUID.fromString(idSoal);
        String namaPengguna = userService.getCurrentUser().getUsername();
        Pengguna pengguna = penggunaRepository.findByNamaPengguna(namaPengguna).orElse(null);
        Soal soal = soalRepository.findById(soalId).orElse(null);
        Ujian ujian = ujianRepository.findBySoalAndPengguna(soal, pengguna).orElse(null);
        ResponseWithMessage responseWithMessage = new ResponseWithMessage();
        if (ujian != null) {
            responseWithMessage.setMessage("Ujian Sudah Ada, Gagal Digenerate!");
        } else {
            if (soal != null){
                List<BankSoal> bankSoalList = bankSoalRepository.findBySoal(soal);
                int lengthBankSoal = bankSoalList.size();
                if (lengthBankSoal == 0){
                    responseWithMessage.setMessage("Bank Soal Kosong, Gagal Digenerate!");
                }else {
                    if (soal.getAcakSoal() == AcakSoal.ACAK){
                        List<JawabanDto> jawabanDtoList = new ArrayList<>();
                        for (BankSoal bankSoalItem : bankSoalList) {

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

                            Collections.shuffle(listPilihan);

                            jawabanDto.setPilihanA(listPilihan.getFirst());
                            jawabanDto.setPilihanB(listPilihan.get(1));
                            jawabanDto.setPilihanC(listPilihan.get(2));
                            jawabanDto.setPilihanD(listPilihan.get(3));
                            jawabanDto.setPilihanE(listPilihan.getLast());

                            jawabanDtoList.add(jawabanDto);
                        }

                        Collections.shuffle(jawabanDtoList);
                        String listJawabanJson = convertListJawabanToJson(jawabanDtoList);

                        Date durasiSoal = soal.getDurasiSoal();
                        Calendar calendarDurasiSoal = Calendar.getInstance();
                        calendarDurasiSoal.setTime(durasiSoal);

                        Date waktuMulaiSoal = soal.getWaktuMulaiSoal();
                        Calendar calendarWaktuMulaiSoal = Calendar.getInstance();
                        calendarWaktuMulaiSoal.setTime(waktuMulaiSoal);

                        calendarWaktuMulaiSoal.add(Calendar.HOUR_OF_DAY, calendarDurasiSoal.get(Calendar.HOUR_OF_DAY));
                        calendarWaktuMulaiSoal.add(Calendar.MINUTE, calendarDurasiSoal.get(Calendar.MINUTE));
                        calendarWaktuMulaiSoal.add(Calendar.SECOND, calendarDurasiSoal.get(Calendar.SECOND));

                        Date waktuSelesaiUjian = calendarWaktuMulaiSoal.getTime();

                        Ujian ujianBaru = new Ujian();
                        ujianBaru.setSoal(soal);
                        ujianBaru.setListJawabanUjian(listJawabanJson);
                        ujianBaru.setStatusUjian(StatusUjian.PROSES);
                        ujianBaru.setPengguna(pengguna);
                        ujianBaru.setWaktuSelesaiUjian(waktuSelesaiUjian);
                        ujianRepository.save(ujianBaru);

                        responseWithMessage.setMessage("Belum Selesai Ngeti - Acak");
                    }else {
                        List<JawabanDto> jawabanDtoList = new ArrayList<>();
                        for (BankSoal bankSoalItem : bankSoalList) {

                            JawabanDto jawabanDto = new JawabanDto();
                            jawabanDto.setIdBank(bankSoalItem.getId());
                            jawabanDto.setPertanyaan(bankSoalItem.getPertanyaanBankSoal());
                            jawabanDto.setStatusPertanyaan(StatusPertanyaan.BELUM_DIJAWAB);

                            jawabanDto.setPilihanA(bankSoalItem.getPilihanA());
                            jawabanDto.setPilihanB(bankSoalItem.getPilihanB());
                            jawabanDto.setPilihanC(bankSoalItem.getPilihanC());
                            jawabanDto.setPilihanD(bankSoalItem.getPilihanD());
                            jawabanDto.setPilihanE(bankSoalItem.getPilihanE());

                            jawabanDtoList.add(jawabanDto);
                        }

                        String listJawabanJson = convertListJawabanToJson(jawabanDtoList);

                        Date durasiSoal = soal.getDurasiSoal();
                        Calendar calendarDurasiSoal = Calendar.getInstance();
                        calendarDurasiSoal.setTime(durasiSoal);

                        Date waktuMulaiSoal = soal.getWaktuMulaiSoal();
                        Calendar calendarWaktuMulaiSoal = Calendar.getInstance();
                        calendarWaktuMulaiSoal.setTime(waktuMulaiSoal);

                        calendarWaktuMulaiSoal.add(Calendar.HOUR_OF_DAY, calendarDurasiSoal.get(Calendar.HOUR_OF_DAY));
                        calendarWaktuMulaiSoal.add(Calendar.MINUTE, calendarDurasiSoal.get(Calendar.MINUTE));
                        calendarWaktuMulaiSoal.add(Calendar.SECOND, calendarDurasiSoal.get(Calendar.SECOND));

                        Date waktuSelesaiUjian = calendarWaktuMulaiSoal.getTime();

                        Ujian ujianBaru = new Ujian();
                        ujianBaru.setSoal(soal);
                        ujianBaru.setListJawabanUjian(listJawabanJson);
                        ujianBaru.setStatusUjian(StatusUjian.PROSES);
                        ujianBaru.setPengguna(pengguna);
                        ujianBaru.setWaktuSelesaiUjian(waktuSelesaiUjian);
                        ujianRepository.save(ujianBaru);
                        responseWithMessage.setMessage("Belum Selesai Ngetik - Tidak Acak");
                    }
                }
            } else {
                responseWithMessage.setMessage("Soal Tidak Ada, Gagal Digenerate!");
            }
        }
        return responseWithMessage;
    }

    private String convertListJawabanToJson(List<JawabanDto> jawabanDto){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(jawabanDto);
        } catch (JsonProcessingException e){
            return null;
        }
    }
}
