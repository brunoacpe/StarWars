package br.com.letscode.Controller;
import br.com.letscode.Model.ArrayDeRecursos;
import br.com.letscode.Model.Rebelde;
import br.com.letscode.Model.Recursos;
import br.com.letscode.Services.NegociarItensService;
import br.com.letscode.Services.RebeldesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/negociar")
public class NegociarItemController {
    private RebeldesServices rebeldesServices;
    private NegociarItensService negociarItensService;

    @Autowired
    public NegociarItemController(RebeldesServices rebeldesServices, NegociarItensService negociarItensService) {
        this.rebeldesServices = rebeldesServices;
        this.negociarItensService = negociarItensService;
    }

    @GetMapping
    public String init() {
        return "Seja bem vindo à área de negociação Rebeldes ! Negocie com sabedoria ! \n" +
                "Como fazer negociação ? \n" +
                "1- Cada um deve passar uma lista de itens desejado pelo requestParam, ex: {água, água, arma} \n" +
                "2- Passar as informações de vocês pelo RequestBody para fazermos as modificações \n" +
                "Obrigado pela atenção !";
    }

    @PostMapping
    public String negociarItens(@RequestBody ArrayDeRecursos itensDeTrocaRebelde1, @RequestParam("nomeRebelde1") String nomeRebelde1, @RequestParam("nomeRebelde2") String nomeRebelde2) {
        return negociarItensService.negociarItens(itensDeTrocaRebelde1, nomeRebelde1, nomeRebelde2);
    }
    //TODO    n sabia como deixar outra marcaçao pra chamar a atençao mas esse é o jeito q deve ser passado os recursos da troca
    //TODO    os recursos1 é pro rebelde1 e o recursos2 pro rebelde2 testado e funcionado ate o momento :)

//        {
//            "recursos1": {
//            "arma": 1,
//                    "municao": 2,
//                    "agua": 1,
//                    "comida": 0
//        },
//            "recursos2": {
//            "arma": 1,
//                    "municao": 2,
//                    "agua": 1,
//                    "comida": 0
//        }
//        }


     //TODO TESTAR O CÓDIGO obs:está dando bug no requestParam dos itens
}
