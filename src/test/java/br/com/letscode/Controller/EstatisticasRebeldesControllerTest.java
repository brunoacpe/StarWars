package br.com.letscode.Controller;

import br.com.letscode.Model.Rebelde;
import br.com.letscode.Services.RebeldesServices;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mock.*;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class EstatisticasRebeldesControllerTest {

    private MockMvc mockMvc;
    private RebeldesServices rebeldesServices;
    @Autowired
    public EstatisticasRebeldesControllerTest(MockMvc mockMvc, RebeldesServices rebeldesServices){
        this.mockMvc = mockMvc;
        this.rebeldesServices = rebeldesServices;
    }
    @Test
    void getPorcentagemTraidores() throws Exception {
        mockMvc.perform(get("/estatisticas/traidores"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("A porcentagem de traidores na galaxia é de : "+ rebeldesServices.getPorcentagemTraidores()+"%")));
    }

    @Test
    void getPorcentagemRebeldes() throws Exception {
        mockMvc.perform(get("/estatisticas/rebeldes"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("A porcentagem de rebeldes na galaxia é de : "+ rebeldesServices.getPorcentagemRebeldes()+"%")));
    }


    @Test
    void getQuantidadeMedia() throws Exception {
        List<Integer> listMedia = rebeldesServices.getQuantidadeMedia();
        mockMvc.perform(get("/estatisticas/recursos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("A quantidade média de recursos é:\n Armas: %d\nMunição: %d\nComida: %d\nAgua: %d\n",listMedia.get(0),listMedia.get(1),listMedia.get(2),listMedia.get(3)))));
    }

    @Test
    void getQuantidadePontosRebeldes() throws Exception {
        mockMvc.perform(get("/estatisticas/pontos"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(String.format("A quantidade de pontos que os rebeldes ja perderam para os traidores é de: %d.",rebeldesServices.getQuantidadePontos()))));
    }
}