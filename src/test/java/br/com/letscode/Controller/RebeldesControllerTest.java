package br.com.letscode.Controller;

import br.com.letscode.Model.Rebelde;
import br.com.letscode.Services.RebeldesServices;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JsonContent;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class RebeldesControllerTest {
    private MockMvc mockMvc;

    private RebeldesServices rebeldesServices;

    @Autowired
    public RebeldesControllerTest(RebeldesServices rebeldesServices, MockMvc mockMvc){
        this.mockMvc = mockMvc;
        this.rebeldesServices = rebeldesServices;
    }

    @Test
    void listarRebeldes() throws Exception {
        mockMvc.perform(get("/rebeldes")).
                andExpect((ResultMatcher) content().json(new Gson().toJson(rebeldesServices.listarRebeldes())));
    }

    @Test
    void criarRebelde() {
    }

    @Test
    void atualizarLocalizacaoRebelde() {
    }
}