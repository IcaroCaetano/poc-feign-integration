package com.example.poc_feign_integration.integration;

import com.github.tomakehurst.wiremock.client.WireMock;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AddressControllerTest {

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        WireMock.configureFor("localhost", 8089);
        stubFor(get(urlEqualTo("/01001000/json/"))
            .willReturn(okJson("{\"cep\":\"01001-000\",\"logradouro\":\"Praça da Sé\"}")));
    }

    @Test
    void shouldReturnAddressSuccessfully() throws Exception {
        mockMvc.perform(get("/address/01001000"))
            .andExpect(status().isOk());
    }
}

