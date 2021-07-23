package br.com.letscode.Controller;


import br.com.letscode.Services.RebeldesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/estatisticas")
public class EstatisticasRebeldesController {

    private RebeldesServices rebeldesServices;

    @Autowired
    public EstatisticasRebeldesController(RebeldesServices rebeldesServices){
        this.rebeldesServices = rebeldesServices;
    }
    //TODO -- DADOS QUE DEVEM SER ACESSADOS POR ESSE CONTROLLER
    /*
      Pontos perdidos devido a traidores.
      */
    @GetMapping
    @RequestMapping("/traidores")
    public int getPorcentagemTraidores(){
        return rebeldesServices.getPorcentagemTraidores();
    }

    @GetMapping
    @RequestMapping("/rebeldes")
    public int getPorcentagemRebeldes(){
        return rebeldesServices.getPorcentagemRebeldes();
    }

    @GetMapping
    @RequestMapping("/recursos")
    public int getQuantidadeMedia(){
        return rebeldesServices.getQuantidadeMedia();
    }
}
