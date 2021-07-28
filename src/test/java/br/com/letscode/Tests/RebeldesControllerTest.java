package br.com.letscode.Tests;


import br.com.letscode.Controller.RebeldesController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@WebMvcTest
@AutoConfigureMockMvc
public class RebeldesControllerTest {

    private RebeldesController rebeldesController;
    private MockMvc mockMvc;
    
    public RebeldesControllerTest(RebeldesController rebeldesController){
        this.rebeldesController = rebeldesController;
    }
    @Test
    public void testarListarRebeldes() throws Exception {
        String rebelde = "{\n" +
                "  \"nome\": \"string\",\n" +
                "  \"genero\": \"string\",\n" +
                "  \"localizacao\": {\n" +
                "    \"nomeGalaxia\": \"string\",\n" +
                "    \"latitude\": 0,\n" +
                "    \"longitude\": 0\n" +
                "  },\n" +
                "  \"recursos\": {\n" +
                "    \"arma\": 0,\n" +
                "    \"municao\": 0,\n" +
                "    \"agua\": 0,\n" +
                "    \"comida\": 0\n" +
                "  },\n" +
                "  \"reports\": 0,\n" +
                "  \"traitor\": true\n" +
                "}";


    }
}
