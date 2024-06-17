package io.hexlet.controller.api;

import io.hexlet.dto.AuthenticationDTO;
import io.hexlet.dto.RegistrationDTO;
import io.hexlet.exception.ResourceAlreadyExistsException;
import io.hexlet.mapper.UserMapper;
import io.hexlet.repository.UserRepository;
import io.hexlet.util.JWTUtil;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @PostMapping("/login")
    String login(@RequestBody AuthenticationDTO authenticationDTO) {
        var email = authenticationDTO.getEmail();
        var password = authenticationDTO.getPassword();
        var authentication = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(authentication);
        var id = userRepository.findByEmail(email).get().getId();
        var jwt = jwtUtil.generateToken(email, id);
        return jwt.getTokenValue();
    }

    @PostMapping("/registry")
    String registry(@Valid @RequestBody RegistrationDTO registrationDTO)
            throws ResourceAlreadyExistsException {
        var user = userMapper.map(registrationDTO);
        var email = registrationDTO.getEmail();
        var password = registrationDTO.getPassword();
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ResourceAlreadyExistsException("User with email " + email + " already exists");
        }
        var authentication = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(authentication);
        var jwt = jwtUtil.generateToken(email, user.getId());
        return jwt.getTokenValue();
    }
}
