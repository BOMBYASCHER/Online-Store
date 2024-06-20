package io.hexlet.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hexlet.controller.util.ModelGenerator;
import io.hexlet.dto.AuthenticationDTO;
import io.hexlet.dto.RegistrationDTO;
import io.hexlet.mapper.UserMapper;
import io.hexlet.model.User;
import io.hexlet.repository.UserRepository;
import io.hexlet.service.AuthenticationService;
import org.instancio.Instancio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class AuthenticationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private User testUser;
    private RegistrationDTO registrationDTO;

    @BeforeEach
    void setup() {
        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        registrationDTO = Instancio.of(modelGenerator.getRegistrationModel()).create();
    }

    @Test
    void testLogin() throws Exception {
        var password = testUser.getPassword();
        testUser.setPassword(passwordEncoder.encode(password));
        userRepository.save(testUser);
        var authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setEmail(testUser.getEmail());
        authenticationDTO.setPassword(password);
        var request = post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(authenticationDTO));
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(response).isNotEmpty();
    }

    @Test
    void testLoginNonRegisteredUser() throws Exception {
        var email = testUser.getEmail();
        var password = testUser.getPassword();
        var credentials = new AuthenticationDTO();
        credentials.setEmail(email);
        credentials.setPassword(password);
        var request = post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(credentials));
        var response = mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"))
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(response).isEmpty();
    }

    @Test
    void testRegistration() throws Exception {
        var request = post("/api/registry")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(registrationDTO));
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(response).isNotEmpty();
        var user = userRepository.findByEmail(registrationDTO.getEmail());
        assertThat(user).isNotEmpty();
        assertThat(user.get().getId()).isNotNull();
    }

    @Test
    void testRegistrationWithAlreadyRegisteredEmail() throws Exception {
        var request1 = post("/api/registry")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(registrationDTO));
        var response1 = mockMvc.perform(request1)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(response1).isNotEmpty();

        var request2 = post("/api/registry")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(registrationDTO));
        var response2 = mockMvc.perform(request2)
                .andExpect(status().isConflict())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThat(response2).contains(registrationDTO.getEmail());
    }
}
