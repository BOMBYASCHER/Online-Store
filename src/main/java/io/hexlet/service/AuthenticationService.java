package io.hexlet.service;

import io.hexlet.dto.AuthenticationDTO;
import io.hexlet.dto.RegistrationDTO;
import io.hexlet.exception.ResourceAlreadyExistsException;
import io.hexlet.mapper.UserMapper;
import io.hexlet.repository.UserRepository;
import io.hexlet.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    public Jwt authenticate(AuthenticationDTO authenticationDTO) {
        var email = authenticationDTO.getEmail();
        var password = authenticationDTO.getPassword();
        var authentication = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(authentication);
        var id = userRepository.findByEmail(email).get().getId();
        return jwtUtil.generateToken(email, id);
    }

    public Jwt registration(RegistrationDTO registrationDTO)
            throws ResourceAlreadyExistsException {
        var email = registrationDTO.getEmail();
        var password = registrationDTO.getPassword();
        var user = userMapper.map(registrationDTO);
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new ResourceAlreadyExistsException("User with email " + email + " already exists");
        }
        var authentication = new UsernamePasswordAuthenticationToken(email, password);
        authenticationManager.authenticate(authentication);
        return jwtUtil.generateToken(email, user.getId());
    }
}
