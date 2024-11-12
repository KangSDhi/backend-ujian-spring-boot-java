package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SiswaDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.CreatePenggunaAdminRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaCreateBatchRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.request.SiswaRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Kelas;
import dev.kangsdhi.backendujianspringbootjava.entities.Pengguna;
import dev.kangsdhi.backendujianspringbootjava.entities.RolePengguna;
import dev.kangsdhi.backendujianspringbootjava.repository.KelasRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.services.PenggunaService;
import dev.kangsdhi.backendujianspringbootjava.utils.GenerateUtils;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PenggunaServiceImplementation implements PenggunaService {
    
    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private KelasRepository kelasRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    private final GenerateUtils generateUtils = new GenerateUtils();

    @Override
    public ResponseWithMessageAndData<List<SiswaDto>> allSiswa() {
        List<SiswaDto> siswaDtoList = penggunaRepository.findByRolePengguna(RolePengguna.SISWA).stream()
                .map(this::mapToSiswaDto)
                .toList();
        return createResponse(HttpStatus.OK.value(), "Berhasil Mengambil Data", siswaDtoList);
    }

    @Override
    public ResponseWithMessageAndData<List<SiswaDto>> storeSiswaBatch(SiswaCreateBatchRequest siswaCreateBatchRequest) {
        List<Pengguna> siswaList = prepareListSiswaEntity(siswaCreateBatchRequest.getData());
        List<Pengguna> siswaListSaved = penggunaRepository.saveAll(siswaList);
        List<SiswaDto> siswaDtoList = siswaListSaved.stream().map(this::mapToSiswaDto).toList();
        return createResponse(HttpStatus.CREATED.value(), "Berhasil Menyimpan Data", siswaDtoList);
    }

    @Override
    public ResponseWithMessageAndData<Object> createPenggunaRoleAdmin(CreatePenggunaAdminRequest createPenggunaAdminRequest) throws BadRequestException {
        Pengguna checkEmailExist = penggunaRepository.findByEmailPengguna(createPenggunaAdminRequest.getEmailAdmin()).orElse(null);
        if (checkEmailExist != null) {
            throw new BadRequestException("Email Sudah Terdaftar!");
        }
        
        boolean checkPassword = createPenggunaAdminRequest.getKonfimasiPasswordAdmin().equals(createPenggunaAdminRequest.getPasswordAdmin());
        if (!checkPassword) {
            throw new BadRequestException("Password Konfirmasi Tidak Sama!");
        }
        
        Pengguna newPenggunaAdmin = new Pengguna();
        newPenggunaAdmin.setNamaPengguna(createPenggunaAdminRequest.getNamaAdmin());
        newPenggunaAdmin.setEmailPengguna(createPenggunaAdminRequest.getEmailAdmin());
        newPenggunaAdmin.setPasswordPengguna(passwordEncoder.encode(createPenggunaAdminRequest.getPasswordAdmin()));
        newPenggunaAdmin.setRolePengguna(RolePengguna.ADMIN);
        Pengguna storePengguna = penggunaRepository.save(newPenggunaAdmin);

        Map<String, Object> data = new HashMap<>();
        data.put("id", storePengguna.getId());
        data.put("nama_pengguna", storePengguna.getNamaPengguna());
        data.put("email_admin", storePengguna.getEmailPengguna());
        
        ResponseWithMessageAndData<Object> responseWithMessageAndData = new ResponseWithMessageAndData<>();
        responseWithMessageAndData.setHttpCode(HttpStatus.CREATED.value());
        responseWithMessageAndData.setMessage("Berhasil Membuat Admin");
        responseWithMessageAndData.setData(data);
        return responseWithMessageAndData;
    }

    private List<Pengguna> prepareListSiswaEntity(List<SiswaRequest> siswaRequestList) {
        List<Pengguna> penggunaList = new ArrayList<>();
        for (SiswaRequest siswaRequest : siswaRequestList) {
            String password = generateUtils.generatedSixDigitRandomStringNumeric();
            Kelas kelas = kelasRepository.findByKelas(siswaRequest.getKelasSiswa());
            Pengguna pengguna = new Pengguna();
            pengguna.setIdSiswa(siswaRequest.getIdSiswa());
            pengguna.setNamaPengguna(siswaRequest.getNamaSiswa());
            pengguna.setPasswordPengguna(passwordEncoder.encode(password));
            pengguna.setPasswordPlain(password);
            pengguna.setRolePengguna(RolePengguna.SISWA);
            pengguna.setKelas(kelas);
            penggunaList.add(pengguna);
        }
        return penggunaList;
    }



    private SiswaDto mapToSiswaDto(Pengguna pengguna){
        SiswaDto siswaDto = new SiswaDto();
        siswaDto.setId(pengguna.getId().toString());
        siswaDto.setIdSiswa(pengguna.getIdSiswa());
        siswaDto.setNamaSiswa(pengguna.getNamaPengguna());
        siswaDto.setPasswordSiswa(pengguna.getPasswordPlain());
        siswaDto.setKelasSiswa(pengguna.getKelas().getKelas());
        siswaDto.setTingkatSiswa(pengguna.getKelas().getTingkat().getTingkat());
        siswaDto.setJurusanSiswa(pengguna.getKelas().getJurusan().getJurusan());
        siswaDto.setCreatedAt(pengguna.getCreatedAt());
        siswaDto.setUpdatedAt(pengguna.getUpdatedAt());
        return siswaDto;
    }

    private <T> ResponseWithMessageAndData<T> createResponse(int httpCode, String message, T data){
        ResponseWithMessageAndData<T> response = new ResponseWithMessageAndData<>();
        response.setHttpCode(httpCode);
        response.setMessage(message);
        response.setData(data);
        return response;
    }
}
