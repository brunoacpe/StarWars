package br.com.letscode.Controller;

import br.com.letscode.Model.Localizacao;
import br.com.letscode.Model.Rebelde;
import br.com.letscode.Model.Recursos;
import br.com.letscode.Services.RebeldesServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    void reportarRebeldeQueJaSeTornouTraidor() throws Exception {
        Rebelde novoRebelde = new Rebelde("Augusto", "Masculino",
                new Localizacao("Taubaté",0.1234f,12.4345f),
                new Recursos(1,2,3,4),
                3,true);
        rebeldesServices.criarRebelde(novoRebelde);
        mockMvc.perform(post("/reportar?nomeReportado=Augusto"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Já temos a informação de que este rebelde se tornou um traidor."));

    }
    @Test
    void reportarRebeldeQueNaoSeTornouTraidor() throws Exception{
        Rebelde novoRebelde = new Rebelde("Pacheco", "Masculino",
                new Localizacao("Taubaté",0.1234f,12.4345f),
                new Recursos(1,2,3,4),
                0,false);
        rebeldesServices.criarRebelde(novoRebelde);
        mockMvc.perform(post("/reportar?nomeReportado=Augusto"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Traidor reportado!!! Obrigado por contribuir por uma galaxia melhor."));
    }
}