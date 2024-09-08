package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.request.CreatePenggunaAdminRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseWithMessageAndData;
import dev.kangsdhi.backendujianspringbootjava.entities.Pengguna;
import dev.kangsdhi.backendujianspringbootjava.entities.RolePengguna;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.services.PenggunaService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PenggunaServiceImplementation implements PenggunaService {
    
    @Autowired
    private PenggunaRepository penggunaRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
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
}
