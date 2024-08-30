package io.hexlet.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.hexlet.controller.config.PostgreSQLContainerConfig;
import io.hexlet.controller.util.ModelGenerator;
import io.hexlet.dto.AuthenticationDTO;
import io.hexlet.mapper.ProductMapper;
import io.hexlet.model.User;
import io.hexlet.repository.ProductRepository;
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
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@SpringBootTest(classes = PostgreSQLContainerConfig.class)
@AutoConfigureMockMvc
public class CartControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ModelGenerator modelGenerator;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ObjectMapper om;

    private Jwt jwt;
    private User testUser;

    @BeforeEach
    void setup() {
        testUser = Instancio.of(modelGenerator.getUserModel()).create();
        var password = testUser.getPassword();
        var encodedPassword = passwordEncoder.encode(password);
        testUser.setPassword(encodedPassword);
        userRepository.save(testUser);
        AuthenticationDTO authenticationDTO = new AuthenticationDTO();
        authenticationDTO.setEmail(testUser.getEmail());
        authenticationDTO.setPassword(password);
        jwt = authenticationService.authenticate(authenticationDTO);
    }

    @Test
    void testIndex() throws Exception {
        var request = get("/api/cart")
                .with(jwt().jwt(jwt));
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThatJson(response).isArray().hasSize(0);
    }

    @Test
    void testIndexNotAuthenticated() throws Exception {
        var request = get("/api/cart");
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }


    @Test
    void testIndexNotEmptyCart() throws Exception {
        Set<Long> products = new HashSet<>(List.of(1L, 2L, 3L));
        testUser.getCart().addAll(products);
        userRepository.save(testUser);
        var request = get("/api/cart")
                .with(jwt().jwt(jwt));
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThatJson(response).isArray().hasSize(3);
    }

    @Test
    void testAddProductInCart() throws Exception {
        var product = productRepository.findById(1L).map(productMapper::map).get();
        var request = put("/api/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(1))
                .with(jwt().jwt(jwt));
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var user = userRepository.findByEmail(testUser.getEmail()).get();
        assertThat(user.getCart()).hasSize(1);
        assertThatJson(response).isArray().hasSize(1);
        assertThatJson(response).isArray().contains(product);
    }

    @Test
    void testAddProductInCartNotAuthenticated() throws Exception {
        var request = put("/api/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(1));
        mockMvc.perform(request)
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));
    }

    @Test
    void testAddNonExistingProductInCart() throws Exception {
        var request = put("/api/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(9000))
                .with(jwt().jwt(jwt));
        var response = mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        assertThatJson(response).isArray().hasSize(0);
    }

    @Test
    void testDeleteFromCart() throws Exception {
        Set<Long> products = new HashSet<>(List.of(1L, 2L, 3L));
        testUser.getCart().addAll(products);
        userRepository.save(testUser);
        var request = delete("/api/cart")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(2))
                .with(jwt().jwt(jwt));
        var response = mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andReturn()
                .getResponse()
                .getContentAsString();
        var user = userRepository.findByEmail(testUser.getEmail()).get();
        assertThatJson(response).asString().isEmpty();
        assertThat(user.getCart()).hasSize(2);
        assertThat(user.getCart()).doesNotContain(2L);
    }
}
