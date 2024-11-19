package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.data.SiswaDto;
import dev.kangsdhi.backendujianspringbootjava.dto.request.*;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessage;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Kelas;
import dev.kangsdhi.backendujianspringbootjava.entities.Pengguna;
import dev.kangsdhi.backendujianspringbootjava.entities.RolePengguna;
import dev.kangsdhi.backendujianspringbootjava.repository.KelasRepository;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.services.PenggunaService;
import dev.kangsdhi.backendujianspringbootjava.utils.GenerateUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public ResponseWithMessageAndData<SiswaDto> storeSiswa(SiswaCreateRequest siswaCreateRequest) {
        Pengguna siswa = prepareCreateSiswaEntity(siswaCreateRequest);
        Pengguna siswaSaved = penggunaRepository.save(siswa);
        SiswaDto siswaDto = mapToSiswaDto(siswaSaved);
        return createResponse(HttpStatus.CREATED.value(), "Berhasil Menyimpan Data", siswaDto);
    }

    @Override
    public ResponseWithMessageAndData<SiswaDto> updateSiswa(SiswaEditRequest siswaEditRequest) {
        Pengguna siswa = prepareUpdateSiswaEntity(siswaEditRequest);
        Pengguna siswaUpdated = penggunaRepository.save(siswa);
        SiswaDto siswaDto = mapToSiswaDto(siswaUpdated);
        return createResponse(HttpStatus.CREATED.value(), "Berhasil Mengupdate Data", siswaDto);
    }

    @Override
    public ResponseWithMessageAndData<SiswaDto> findSiswaById(String id) throws BadRequestException {
        try {
            UUID siswaId = UUID.fromString(id);
            Pengguna siswa = penggunaRepository.findById(siswaId).orElseThrow(() -> new EntityNotFoundException("Siswa Tidak Ditemukan!"));
            SiswaDto siswaDto = mapToSiswaDto(siswa);
            return createResponse(HttpStatus.OK.value(), "Siswa Ditemukan!", siswaDto);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Format ID Tidak Valid!");
        }
    }

    @Override
    public ResponseWithMessage deleteSiswa(String id) throws BadRequestException {
        try {
            UUID siswaId = UUID.fromString(id);
            penggunaRepository.deleteById(siswaId);
            return createResponse(HttpStatus.OK.value(), "Berhasil Menghapus Siswa!");
        } catch (IllegalArgumentException e){
            throw new BadRequestException("Format ID TIdak Valid!");
        }
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
        responseWithMessageAndData.setHttp_code(HttpStatus.CREATED.value());
        responseWithMessageAndData.setMessage("Berhasil Membuat Admin");
        responseWithMessageAndData.setData(data);
        return responseWithMessageAndData;
    }

    private List<Pengguna> prepareListSiswaEntity(List<SiswaItemBatchRequest> siswaItemBatchRequestList) {
        List<Pengguna> penggunaList = new ArrayList<>();
        for (SiswaItemBatchRequest siswaItemBatchRequest : siswaItemBatchRequestList) {
            String password = generateUtils.generatedSixDigitRandomStringNumeric();
            Kelas kelas = kelasRepository.findByKelas(siswaItemBatchRequest.getKelas());
            Pengguna pengguna = new Pengguna();
            pengguna.setIdSiswa(siswaItemBatchRequest.getId_siswa());
            pengguna.setNamaPengguna(siswaItemBatchRequest.getNama_siswa());
            pengguna.setPasswordPengguna(passwordEncoder.encode(password));
            pengguna.setPasswordPlain(password);
            pengguna.setRolePengguna(RolePengguna.SISWA);
            pengguna.setKelas(kelas);
            penggunaList.add(pengguna);
        }
        return penggunaList;
    }

    private Pengguna prepareCreateSiswaEntity(SiswaCreateRequest siswaCreateRequest) {
        Kelas kelas = kelasRepository.findByKelas(siswaCreateRequest.getKelas());
        Pengguna pengguna = new Pengguna();
        pengguna.setNamaPengguna(siswaCreateRequest.getNama_siswa());
        pengguna.setIdSiswa(siswaCreateRequest.getId_siswa());
        pengguna.setPasswordPengguna(passwordEncoder.encode(siswaCreateRequest.getPassword()));
        pengguna.setPasswordPlain(siswaCreateRequest.getPassword());
        pengguna.setRolePengguna(RolePengguna.SISWA);
        pengguna.setKelas(kelas);
        return pengguna;
    }

    private Pengguna prepareUpdateSiswaEntity(SiswaEditRequest siswaEditRequest) {
        Kelas kelas = kelasRepository.findByKelas(siswaEditRequest.getKelas());
        UUID siswaId = UUID.fromString(siswaEditRequest.getId());
        Pengguna pengguna = penggunaRepository.findById(siswaId).orElseThrow(() -> new EntityNotFoundException("Pengguna Tidak Ditemukan!"));
        pengguna.setNamaPengguna(siswaEditRequest.getNama_siswa());
        pengguna.setIdSiswa(siswaEditRequest.getId_siswa());
        pengguna.setKelas(kelas);
        if (siswaEditRequest.getPassword() != null) {
            pengguna.setPasswordPengguna(passwordEncoder.encode(siswaEditRequest.getPassword()));
            pengguna.setPasswordPlain(siswaEditRequest.getPassword());
        }
        return pengguna;
    }

    private SiswaDto mapToSiswaDto(Pengguna pengguna){
        SiswaDto siswaDto = new SiswaDto();
        siswaDto.setId(pengguna.getId().toString());
        siswaDto.setId_siswa(pengguna.getIdSiswa());
        siswaDto.setNama_siswa(pengguna.getNamaPengguna());
        siswaDto.setPassword(pengguna.getPasswordPlain());
        siswaDto.setKelas(pengguna.getKelas().getKelas());
        siswaDto.setTingkat(pengguna.getKelas().getTingkat().getTingkat());
        siswaDto.setJurusan(pengguna.getKelas().getJurusan().getJurusan());
        siswaDto.setCreatedAt(pengguna.getCreatedAt());
        siswaDto.setUpdatedAt(pengguna.getUpdatedAt());
        return siswaDto;
    }

    private <T> ResponseWithMessageAndData<T> createResponse(int httpCode, String message, T data){
        ResponseWithMessageAndData<T> response = new ResponseWithMessageAndData<>();
        response.setHttp_code(httpCode);
        response.setMessage(message);
        response.setData(data);
        return response;
    }

    private ResponseWithMessage createResponse(int httpCode, String message) {
        ResponseWithMessage response = new ResponseWithMessage();
        response.setHttp_code(httpCode);
        response.setMessage(message);
        return response;
    }
}
