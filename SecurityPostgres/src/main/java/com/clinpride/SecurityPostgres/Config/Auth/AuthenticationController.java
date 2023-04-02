package com.clinpride.SecurityPostgres.Config.Auth;

import com.clinpride.SecurityPostgres.Services.AuthenticationService;
import com.clinpride.SecurityPostgres.models.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.rmi.registry.Registry;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody AppUser appUser){
        return ResponseEntity.ok(authenticationService.register(appUser));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate(@RequestBody AppUser appUser){
        return ResponseEntity.ok(authenticationService.authenticate(appUser));

    }


}
