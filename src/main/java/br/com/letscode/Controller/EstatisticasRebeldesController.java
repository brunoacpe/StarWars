package br.com.letscode.Controller;


import br.com.letscode.Services.RebeldesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
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

    @GetMapping
    @RequestMapping("/traidores")
    @ResponseStatus(HttpStatus.OK)
    public String getPorcentagemTraidores(){

        return "A porcentagem de traidores na galaxia é de : "+rebeldesServices.getPorcentagemTraidores()+ "%";
    }

    @GetMapping
    @RequestMapping("/rebeldes")
    @ResponseStatus(HttpStatus.OK)
    public String getPorcentagemRebeldes(){
        return "A porcentagem de rebeldes na galaxia é de : "+rebeldesServices.getPorcentagemRebeldes()+ "%";
    }

    @GetMapping
    @RequestMapping("/recursos")
    public String getQuantidadeMedia(){
        List<Integer> listMedia = rebeldesServices.getQuantidadeMedia();
        return String.format("A quantidade média de recursos é:\n Armas: %d\nMunição: %d\nComida: %d\nAgua: %d\n",listMedia.get(0),listMedia.get(1),listMedia.get(2),listMedia.get(3));
    }

    @GetMapping
    @RequestMapping("/pontos")
    public String getQuantidadePontosRebeldes(){
        return String.format("A quantidade de pontos que os rebeldes ja perderam para os traidores é de: %d.",rebeldesServices.getQuantidadePontos());
    }
}
