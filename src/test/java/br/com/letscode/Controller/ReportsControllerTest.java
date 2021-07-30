package br.com.letscode.Controller;

import br.com.letscode.Services.RebeldesServices;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ReportsControllerTest {

    private MockMvc mockMvc;
    private RebeldesServices rebeldesServices;
    @Autowired
    public ReportsControllerTest(MockMvc mockMvc,RebeldesServices rebeldesServices){
        this.mockMvc = mockMvc;
        this.rebeldesServices = rebeldesServices;
    }
    @Test
    void reportarRebeldeQueJaSeTornouTraidor() {

    }
    @Test
    void reportarRebeldeQueNaoSeTornouTraidor(){

    }
    @Test
    void reportarRebeldeQueVaiSeTornarTraidor(){

    }
}