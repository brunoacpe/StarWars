package br.com.letscode.Controller;
import br.com.letscode.Model.Rebelde;
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

    @Autowired
    public NegociarItemController(RebeldesServices rebeldesServices) {
        this.rebeldesServices = rebeldesServices;
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
    public String negociarItens(@RequestParam("itensDeTrocaRebelde1") ArrayList<String> itensDeTrocaRebelde1, @RequestParam("itensDeTrocaRebelde2") ArrayList<String> itensDeTrocaRebelde2, @RequestParam("nomeRebelde1") String nomeRebelde1, @RequestParam("nomeRebelde2") String nomeRebelde2) {
        Optional<Rebelde> rebelde1 = rebeldesServices.listarRebeldes().stream().filter(r -> r.getNome().equals(nomeRebelde1)).findFirst();
        Optional<Rebelde> rebelde2 = rebeldesServices.listarRebeldes().stream().filter(r -> r.getNome().equals(nomeRebelde2)).findFirst();
        if (rebelde1.isPresent() && rebelde2.isPresent()) {
            if (rebelde1.get().isTraitor() || rebelde2.get().isTraitor()) {
                return "Há um traidor entre nós !! Não ocorrerá nenhuma negociação !";
            }
            if (pontuacaoDosItens(itensDeTrocaRebelde1) != pontuacaoDosItens(itensDeTrocaRebelde2)) {
                return "Pontuação dos itens não estão iguais, rebeldes";
            }
            if (trocaDeItens(rebelde1, itensDeTrocaRebelde1, rebelde2, itensDeTrocaRebelde2)) {
                return "Negociação dos itens feita com sucesso !";
            }
            return "A quantidade de itens da lista precisa ser válida com os seus recursos, por favor tente novamente rebeldes !";
        }
        return "Não há rebeldes com esse nome !";
    }

    public long pontuacaoDosItens(List<String> itensDeTroca){
        long numeroDeArmas = itensDeTroca.stream().filter(item -> item.equalsIgnoreCase("arma")).count();
        long numeroDeMunicao = itensDeTroca.stream().filter(item -> item.equalsIgnoreCase("munição")).count();
        long numeroDeAguas = itensDeTroca.stream().filter(item -> item.equalsIgnoreCase("água")).count();
        long numeroDeComida = itensDeTroca.stream().filter(item -> item.equalsIgnoreCase("comida")).count();
        return (numeroDeArmas * 4) + (numeroDeMunicao * 3) + ( numeroDeAguas * 2) + ( numeroDeComida * 1) ;
    }

    public boolean trocaDeItens(Optional<Rebelde> rebelde1, List<String> listaDoRebelde1, Optional<Rebelde> rebelde2, List<String> listaDoRebelde2){
        if(verificacaoDeValidadeDaLista(rebelde1, listaDoRebelde1) && verificacaoDeValidadeDaLista(rebelde2, listaDoRebelde2)){
            //================= (+) INVENTÁRIO REBELDES 1
            rebelde1.get().getRecursos().setAgua(Math.toIntExact(rebelde1.get().getRecursos().getAgua() + qtdDeCadaItem(listaDoRebelde2, "água") - qtdDeCadaItem(listaDoRebelde1, "água")));
            rebelde1.get().getRecursos().setMunicao(Math.toIntExact(rebelde1.get().getRecursos().getMunicao() + qtdDeCadaItem(listaDoRebelde2, "municão") - qtdDeCadaItem(listaDoRebelde1, "munição")));
            rebelde1.get().getRecursos().setComida(Math.toIntExact(rebelde1.get().getRecursos().getComida() + qtdDeCadaItem(listaDoRebelde2, "comida") - qtdDeCadaItem(listaDoRebelde1, "comida")));
            rebelde1.get().getRecursos().setArma(Math.toIntExact(rebelde1.get().getRecursos().getArma() + qtdDeCadaItem(listaDoRebelde2, "arma") - qtdDeCadaItem(listaDoRebelde1, "arma")));

            //================ (+) INVENTÁRIO REBELDES 2
            rebelde2.get().getRecursos().setAgua(Math.toIntExact(rebelde2.get().getRecursos().getAgua() + qtdDeCadaItem(listaDoRebelde1, "água") - qtdDeCadaItem(listaDoRebelde2,"água")));
            rebelde2.get().getRecursos().setArma(Math.toIntExact(rebelde2.get().getRecursos().getArma() + qtdDeCadaItem(listaDoRebelde1, "arma") - qtdDeCadaItem(listaDoRebelde2, "arma")));
            rebelde2.get().getRecursos().setMunicao(Math.toIntExact(rebelde2.get().getRecursos().getMunicao() + qtdDeCadaItem(listaDoRebelde1, "munição") - qtdDeCadaItem(listaDoRebelde2, "munição")));
            rebelde2.get().getRecursos().setComida(Math.toIntExact(rebelde2.get().getRecursos().getComida() + qtdDeCadaItem(listaDoRebelde1, "comida") - qtdDeCadaItem(listaDoRebelde2,"comida")));

            return true;
        }
        return false;
    }

    public long qtdDeCadaItem(List<String> listDoRebelde, String itemDeVerificacao){
        return  listDoRebelde.stream().filter(item -> item.equalsIgnoreCase(itemDeVerificacao)).count();
    }
     public boolean verificacaoDeValidadeDaLista(Optional<Rebelde> rebelde, List<String> listaDoRebelde){
        boolean verificacaoArma = rebelde.get().getRecursos().getArma() < (qtdDeCadaItem(listaDoRebelde, "arma"));
        boolean verificaoMunicao = rebelde.get().getRecursos().getMunicao() < qtdDeCadaItem(listaDoRebelde, "municao");
        boolean verificacoAgua = rebelde.get().getRecursos().getAgua() < (qtdDeCadaItem(listaDoRebelde,"água"));
        boolean verificacaoComida = rebelde.get().getRecursos().getComida() < qtdDeCadaItem(listaDoRebelde, "comida");
        if(verificacaoArma && verificaoMunicao && verificacoAgua && verificacaoComida){
            return true;
        }
        return false;
     }

     //TODO TESTAR O CÓDIGO obs:está dando bug no requestParam dos itens
}
