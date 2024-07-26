package dev.kangsdhi.backendujianspringbootjava.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.kangsdhi.backendujianspringbootjava.dto.response.ResponseError;
import dev.kangsdhi.backendujianspringbootjava.entities.RolePengguna;
import dev.kangsdhi.backendujianspringbootjava.services.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    private final UserService userService;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

        http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(request ->
                        request.requestMatchers("/api/auth/signout").authenticated()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/health/**").permitAll()
                                .requestMatchers("/api/admin/**").hasAnyAuthority(RolePengguna.ADMIN.name())
                                .requestMatchers("/api/guru/**").hasAnyAuthority(RolePengguna.GURU.name())
                                .requestMatchers("/api/siswa/**").hasAnyAuthority(RolePengguna.SISWA.name())
                                .anyRequest().authenticated())
                .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider()).addFilterBefore(
                        jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            ResponseError<String> responseError = new ResponseError<>();
                            responseError.setHttpCode(HttpStatus.FORBIDDEN.value());
                            responseError.setErrors(accessDeniedException.getMessage());
                            ResponseEntity<ResponseError<String>> responseEntity = ResponseEntity.status(HttpStatus.FORBIDDEN)
                                    .body(responseError);
                            ObjectMapper objectMapper = new ObjectMapper();
                            String jsonResponse = objectMapper.writeValueAsString(responseEntity.getBody());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpStatus.FORBIDDEN.value());
                            response.getWriter().write(jsonResponse);
                            response.getWriter().flush();
                            logger.error(accessDeniedException.getMessage());
                        })
                        .authenticationEntryPoint((request, response, authException) -> {
                            ResponseError<String> responseError = new ResponseError<>();
                            responseError.setHttpCode(HttpStatus.UNAUTHORIZED.value());
                            responseError.setErrors(authException.getMessage());
                            ResponseEntity<ResponseError<String>> responseEntity = ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                    .body(responseError);
                            ObjectMapper objectMapper = new ObjectMapper();
                            String jsonResponse = objectMapper.writeValueAsString(responseEntity.getBody());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setStatus(HttpStatus.UNAUTHORIZED.value());
                            response.getWriter().write(jsonResponse);
                            response.getWriter().flush();
                            logger.error(authException.getMessage());
                        })
                );
        return http.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userService.userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
