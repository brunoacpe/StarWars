package br.com.letscode.Controller;

import br.com.letscode.Services.RebeldesServices;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

class NegociarItemControllerTest {

    private MockMvc mockMvc;

    private RebeldesServices rebeldesServices;
    @Autowired
    public NegociarItemControllerTest(MockMvc mockMvc, RebeldesServices rebeldesServices){
        this.mockMvc = mockMvc;
        this.rebeldesServices = rebeldesServices;
    }
    @Test
    void negociarItens() {

    }
}