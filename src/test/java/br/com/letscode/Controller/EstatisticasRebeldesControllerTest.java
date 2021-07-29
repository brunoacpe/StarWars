package br.com.letscode.Controller;

import br.com.letscode.Services.RebeldesServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.Mock.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EstatisticasRebeldesController.class)
class EstatisticasRebeldesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void getPorcentagemTraidores() throws Exception {
        mockMvc.perform(get("/estatisticas/traidores"))
                .andExpect(content().string("A porcentagem de traidores na galaxia é de : 0.0%"));
    }

    @Test
    void getPorcentagemRebeldes() {
    }

    @Test
    void getQuantidadeMedia() {
    }

    @Test
    void getQuantidadePontosRebeldes() {
    }
}