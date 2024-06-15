package io.hexlet.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrlPattern;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;

@SpringBootTest
@AutoConfigureMockMvc
public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void testRoot() throws Exception {
        var request = get("/");
        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/catalog"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void testCatalog() throws Exception {
        var request = get("/catalog");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void testCart() throws Exception {
        var request = get("/cart")
                .with(jwt());
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void testCartWithoutAuthentication() throws Exception {
        var request = get("/cart");
        mockMvc.perform(request)
                .andExpect(status().isFound())
                .andExpect(redirectedUrlPattern("**/login"))
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void testLogin() throws Exception {
        var request = get("/login");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }

    @Test
    void testRegistry() throws Exception {
        var request = get("/registry");
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
    }
}
