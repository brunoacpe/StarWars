package br.com.letscode.Controller;

import br.com.letscode.Model.Localizacao;
import br.com.letscode.Model.Rebelde;
import br.com.letscode.Model.Recursos;
import br.com.letscode.Services.RebeldesServices;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
class RebeldesControllerTest {

    private MockMvc mockMvc;

    private RebeldesServices rebeldesServices;
    @Autowired
    public RebeldesControllerTest(MockMvc mockMvc, RebeldesServices rebeldesServices){
        this.mockMvc = mockMvc;
        this.rebeldesServices = rebeldesServices;
    }
    @Test
    void listarRebeldes() throws Exception{
        List<Rebelde> listarRebeldes = rebeldesServices.listarRebeldes();
        mockMvc.perform(get("/rebeldes").contentType(MediaType.ALL))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(new Gson().toJson(listarRebeldes)));

    }

    @Test
    void criarRebelde() throws Exception {
        Rebelde rebelde = new Rebelde();
        rebelde.setNome("Vitoria");
        rebelde.setGenero("Feminino");
        rebelde.setRecursos(new Recursos(1,2,3,4));
        rebelde.setReports(0);
        rebelde.setTraitor(false);
        rebelde.setLocalizacao(new Localizacao("Taubate", 9.096f, 6.874f));
        String json = new Gson().toJson(rebelde);
        mockMvc.perform(post("/rebeldes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isCreated())
                .andReturn();

    }

    @Test
    void atualizarLocalizacaoRebeldeComSucesso() throws Exception {
        Localizacao localizacao = new Localizacao("Rio de Janeiro", 4.321f, 5.876f);
        String json = new Gson().toJson(localizacao);
        mockMvc.perform(put("/rebeldes/atualizarLocalização?nomeRebelde=Vitoria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Localização atualizada !!! Bom saber por onde você anda."));
    }

    @Test
    void atualizarLocalizacaoTraidor() throws  Exception{
        Localizacao localizacao = new Localizacao("Rio de Janeiro", 4.321f, 5.876f);
        String json = new Gson().toJson(localizacao);
        mockMvc.perform(put("/rebeldes/atualizarLocalização?nomeRebelde=Felipe")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Você não vai conseguir atualizar sua localização, traidor."));
    }

    @Test
    void atualizarLocalizacaoRebeldeNaoExistente() throws  Exception{
        Localizacao localizacao = new Localizacao("Rio de Janeiro", 4.321f, 5.876f);
        String json = new Gson().toJson(localizacao);
        mockMvc.perform(put("/rebeldes/atualizarLocalização?nomeRebelde=ABCDEFH")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("Não existe nenhum rebelde com este nome."));
    }

}