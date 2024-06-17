package io.hexlet.controller.api;

import io.hexlet.dto.AuthenticationDTO;
import io.hexlet.dto.RegistrationDTO;
import io.hexlet.exception.ResourceAlreadyExistsException;
import io.hexlet.service.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    @Autowired
    private AuthenticationService authenticationService;
    @PostMapping("/login")
    String login(@RequestBody AuthenticationDTO authenticationDTO) {
        return authenticationService.authenticate(authenticationDTO)
                .getTokenValue();
    }

    @PostMapping("/registry")
    String registry(@Valid @RequestBody RegistrationDTO registrationDTO)
            throws ResourceAlreadyExistsException {
        return authenticationService.registration(registrationDTO)
                .getTokenValue();
    }
}
