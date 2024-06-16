package dev.kangsdhi.backendujianspringbootjava.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "pengguna")
public class Pengguna implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "uuid", updatable = false, nullable = false)
    private UUID id;

    @Column(name = "id_siswa", nullable = true, unique = true)
    private String idSiswa;

    @Column(name = "nama_pengguna", nullable = false)
    private String namaPengguna;

    @Column(name = "email_pengguna", nullable = true, unique = true)
    private String emailPengguna;

    @Column(name = "password_pengguna", nullable = false)
    private String passwordPengguna;

    @Column(name = "password_plain", nullable = true)
    private String passwordPlain;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_pengguna", nullable = false)
    private RolePengguna rolePengguna;

    @ManyToOne
    @JoinColumn(name = "kelas_id", referencedColumnName = "id", nullable = true)
    private Kelas kelas;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(rolePengguna.name()));
    }

    @Override
    public String getPassword() {
        return passwordPengguna;
    }

    @Override
    public String getUsername() {
        return emailPengguna;
    }

    @Override
    public boolean isAccountNonExpired() {
//        return UserDetails.super.isAccountNonExpired();
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        return UserDetails.super.isAccountNonLocked();
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        return UserDetails.super.isCredentialsNonExpired();
        return true;
    }

    @Override
    public boolean isEnabled() {
//        return UserDetails.super.isEnabled();
        return true;
    }
}
