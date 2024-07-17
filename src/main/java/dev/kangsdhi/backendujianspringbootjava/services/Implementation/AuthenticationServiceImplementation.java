package dev.kangsdhi.backendujianspringbootjava.services.Implementation;

import dev.kangsdhi.backendujianspringbootjava.dto.SignInRequest;
import dev.kangsdhi.backendujianspringbootjava.dto.SignInResponse;
import dev.kangsdhi.backendujianspringbootjava.dto.SignOutResponse;
import dev.kangsdhi.backendujianspringbootjava.entities.Pengguna;
import dev.kangsdhi.backendujianspringbootjava.entities.RolePengguna;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import dev.kangsdhi.backendujianspringbootjava.services.AuthenticationService;
import dev.kangsdhi.backendujianspringbootjava.services.JWTService;
import dev.kangsdhi.backendujianspringbootjava.utils.ValidateUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImplementation implements AuthenticationService {

    private final PenggunaRepository penggunaRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public SignInResponse signIn(SignInRequest signInRequest) {

        Pengguna pengguna = new Pengguna();
        ValidateUtils validateUtils = new ValidateUtils();

        if (validateUtils.validateEmailFormat(signInRequest.getEmailOrIdSiswa())){
            pengguna = penggunaRepository.findByEmailPengguna(signInRequest.getEmailOrIdSiswa()).orElseThrow(() -> new UsernameNotFoundException("Pengguna Tidak Ditemukan!"));
            if (pengguna != null && passwordEncoder.matches(signInRequest.getPassword(), pengguna.getPassword())) {
                // check this out
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(pengguna.getUsername(), signInRequest.getPassword()));
            } else {
                throw new UsernameNotFoundException("Pengguna Tidak Ditemukan!");
            }
        } else {
            pengguna = penggunaRepository.findByIdSiswa(signInRequest.getEmailOrIdSiswa()).orElseThrow(() -> new UsernameNotFoundException("Siswa Tidak Ditemukan!"));
            if (pengguna != null && passwordEncoder.matches(signInRequest.getPassword(), pengguna.getPassword())) {
                // check this out
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(pengguna.getUsername(), signInRequest.getPassword()));
            } else {
                throw new UsernameNotFoundException("Siswa Tidak Ditemukan!");
            }
        }

        String jwtToken = jwtService.generateToken(pengguna);

        SignInResponse<Object> signInResponse = new SignInResponse<>();
        signInResponse.setHttpCode(HttpStatus.OK.value());
        signInResponse.setMessage("Berhasil Login");

        Map<String, String> data = new HashMap<>();
        data.put("token", jwtToken);
        data.put("level", pengguna.getRolePengguna().name());
        data.put("nama_pengguna", pengguna.getNamaPengguna());
        if (pengguna.getRolePengguna() == RolePengguna.SISWA){
            data.put("tingkat", pengguna.getKelas().getTingkat().getTingkat());
            data.put("jurusan", pengguna.getKelas().getJurusan().getJurusan());
        }
        signInResponse.setData(data);

        return signInResponse;
    }

    @Override
    public SignOutResponse signOut() {
        SignOutResponse signOutResponse = new SignOutResponse();
        signOutResponse.setHttpCode(HttpStatus.OK.value());
        signOutResponse.setMessage("Berhasil Logout");
        return signOutResponse;
    }

    @Override
    public UserDetails getCurrentUser() {
        return (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
