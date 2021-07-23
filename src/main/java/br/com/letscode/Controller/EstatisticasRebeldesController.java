package br.com.letscode.Controller;


import br.com.letscode.Excecoes.NaoHaTraidoresException;
import br.com.letscode.Services.RebeldesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public String getPorcentagemTraidores() throws NaoHaTraidoresException {

        return "A porcentagem de traidores na galaxia é de : "+rebeldesServices.getPorcentagemTraidores()+ "%";
    }

    @GetMapping
    @RequestMapping("/rebeldes")
    public String getPorcentagemRebeldes() throws NaoHaTraidoresException {
        return "A porcentagem de rebeldes na galaxia é de : "+rebeldesServices.getPorcentagemRebeldes()+ "%";
    }

    @GetMapping
    @RequestMapping("/recursos")
    public String getQuantidadeMedia(){
        List<Integer> listMedia = rebeldesServices.getQuantidadeMedia();
        return String.format("A quantidade média de recursos é:\n Armas: %d\nMunição: %d\nComida: %d\nAgua: %d\n",listMedia.get(0),listMedia.get(1),listMedia.get(2),listMedia.get(3));
    }
}
