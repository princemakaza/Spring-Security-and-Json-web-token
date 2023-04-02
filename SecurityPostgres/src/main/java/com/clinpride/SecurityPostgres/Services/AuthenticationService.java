package com.clinpride.SecurityPostgres.Services;

import com.clinpride.SecurityPostgres.Config.Auth.AuthenticationResponse;
import com.clinpride.SecurityPostgres.Config.JwtService;
import com.clinpride.SecurityPostgres.UserRepo.UserRepository;
import com.clinpride.SecurityPostgres.models.AppUser;
import com.clinpride.SecurityPostgres.models.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(AppUser appUser) {
        var user = appUser.builder()
                .firstName(appUser.getFirstName())
                .lastName(appUser.getFirstName())
                .email(appUser.getFirstName())
                .password(passwordEncoder.encode(appUser.getPassword()))
                .role(Role.USER)
                .build();
                repository.save(user);
                var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }

    public AuthenticationResponse authenticate(AppUser appUser) {
    authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    appUser.getEmail(),
                    appUser.getPassword()
            )
    );
            var user = repository.findByEmail(appUser.getEmail())
                    .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder().token(jwtToken).build();
    }
}
