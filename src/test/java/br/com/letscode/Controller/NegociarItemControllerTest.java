package br.com.letscode.Controller;

import br.com.letscode.Model.ArrayDeRecursos;
import br.com.letscode.Model.Recursos;
import br.com.letscode.Services.RebeldesServices;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class NegociarItemControllerTest {

    private MockMvc mockMvc;

    private RebeldesServices rebeldesServices;
    @Autowired
    public NegociarItemControllerTest(MockMvc mockMvc, RebeldesServices rebeldesServices){
        this.mockMvc = mockMvc;
        this.rebeldesServices = rebeldesServices;
    }

    @Test
    void negociarItensTraidor() throws Exception {
        String json = new Gson().toJson(new ArrayDeRecursos(new Recursos(2,2,2,2), new Recursos(1,2,4,3)));
        mockMvc.perform(post("/negociar/?nomeRebelde1=Felipe&nomeRebelde2=Vitoria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Há um traidor entre nós !! Não ocorrerá nenhuma negociação !"));
    }

    @Test
    void negociarItensPontuacaoErrada() throws Exception {
        String json = new Gson().toJson(new ArrayDeRecursos(new Recursos(1,2,4,3), new Recursos(2,2,2,2)));
        mockMvc.perform(post("/negociar/?nomeRebelde1=Bruno&nomeRebelde2=Vitoria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Pontuação dos itens não estão iguais, rebeldes"));
    }


    @Test
    void negociarItensComSucesso() throws Exception {
        String json = new Gson().toJson(new ArrayDeRecursos(new Recursos(0,1,0,1),new Recursos(1,0,0,0)));
        mockMvc.perform(post("/negociar/?nomeRebelde1=Bruno&nomeRebelde2=Vitoria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Negociação dos itens feita com sucesso !"));
    }

    @Test
    void negociarItensRebeldeInexsistente() throws Exception {
        String json = new Gson().toJson(new ArrayDeRecursos(new Recursos(0,1,0,1),new Recursos(1,0,0,0)));
        mockMvc.perform(post("/negociar/?nomeRebelde1=ABCDE&nomeRebelde2=Vitoria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("Não há rebeldes com esse nome !"));
    }

    @Test
    void negociarItensListaInvalida() throws Exception {
        String json = new Gson().toJson(new ArrayDeRecursos(new Recursos(10,0,0,0),new Recursos(0,10,0,10)));
        mockMvc.perform(post("/negociar/?nomeRebelde1=Bruno&nomeRebelde2=Vitoria")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("A quantidade de itens da lista precisa ser válida com os seus recursos, por favor tente novamente rebeldes !"));
    }
}