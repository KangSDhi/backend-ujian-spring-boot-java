package dev.kangsdhi.backendujianspringbootjava.database.seeder;

import dev.kangsdhi.backendujianspringbootjava.entities.Pengguna;
import dev.kangsdhi.backendujianspringbootjava.entities.RolePengguna;
import dev.kangsdhi.backendujianspringbootjava.repository.PenggunaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminSeeder {

    @Autowired
    private PenggunaRepository penggunaRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void seedAdmin(){
        Pengguna admin = penggunaRepository.findByRolePengguna(RolePengguna.ADMIN);
        if (admin == null) {
            Pengguna adminBaru = new Pengguna();
            adminBaru.setNamaPengguna("Kang Admin");
            adminBaru.setEmailPengguna("kangadmin@gmail.com");
            adminBaru.setPasswordPengguna(passwordEncoder.encode("qwerty"));
            adminBaru.setRolePengguna(RolePengguna.ADMIN);
            penggunaRepository.save(adminBaru);
            System.out.println("Membuat Data Admin Baru ✅");
        }
    }
}
