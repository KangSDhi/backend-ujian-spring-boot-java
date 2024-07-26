package dev.kangsdhi.backendujianspringbootjava.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService {
    UserDetailsService userDetailsService();
    UserDetails getCurrentUser();
}
