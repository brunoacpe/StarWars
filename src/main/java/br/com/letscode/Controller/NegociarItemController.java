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
import java.util.List;

@RestController
@RequestMapping("/negociar")
public class NegociarItemController {
    private RebeldesServices rebeldesServices;

    @Autowired
    public NegociarItemController(RebeldesServices rebeldesServices){
        this.rebeldesServices = rebeldesServices;
    }

    @GetMapping
    public String init(){
        return "Seja bem vindo à área de negociação Rebeldes ! Negocie com sabedoria ! \n" +
                "Como fazer negociação ? \n" +
                "1- Cada um deve passar uma lista de itens desejado pelo requestParam, ex: {água, água,arma} \n" +
                "2- E passar as informações de vocês pelo RequestBody para fazermos as modificações \n" +
                "Obrigado pela atenção !";
    }

    @PostMapping
    public String negociarItens(@RequestParam List<String> itensDeTrocaRebelde1, @RequestParam List<String> itensDeTrocaRebelde2, @RequestBody Rebelde rebelde1, @RequestBody Rebelde rebelde2){
        if(rebelde1.isTraitor() || rebelde2.isTraitor()){
            return "Há um traidor entre nós !! Não ocorrerá nenhuma negociação !";
        }
        if(pontuacaoDosItens(itensDeTrocaRebelde1) != pontuacaoDosItens(itensDeTrocaRebelde2)){
            return "Pontuação dos itens não estão iguais, rebeldes";
        }
        if(trocaDeItens(rebelde1,itensDeTrocaRebelde1,rebelde2,itensDeTrocaRebelde2)) {
            return "Negociação dos itens feita com sucesso !";
        }
        return "A quantidade de itens da lista precisa ser válida com os seus recursos, por favor tente novamente rebeldes !";
    }

    public long pontuacaoDosItens(List<String> itensDeTroca){
        long numeroDeArmas = itensDeTroca.stream().filter(item -> item.equalsIgnoreCase("arma")).count();
        long numeroDeMunicao = itensDeTroca.stream().filter(item -> item.equalsIgnoreCase("munição")).count();
        long numeroDeAguas = itensDeTroca.stream().filter(item -> item.equalsIgnoreCase("água")).count();
        long numeroDeComida = itensDeTroca.stream().filter(item -> item.equalsIgnoreCase("comida")).count();
        return (numeroDeArmas * 4) + (numeroDeMunicao * 3) + ( numeroDeAguas * 2) + ( numeroDeComida * 1) ;
    }

    public boolean trocaDeItens(Rebelde rebelde1, List<String> listaDoRebelde1, Rebelde rebelde2, List<String> listaDoRebelde2){
        if(verificacaoDeValidadeDaLista(rebelde1, listaDoRebelde1) && verificacaoDeValidadeDaLista(rebelde2, listaDoRebelde2)){
            //================= (+) INVENTÁRIO REBELDES 1
            rebelde1.getRecursos().setAgua(Math.toIntExact(rebelde1.getRecursos().getAgua() + qtdDeCadaItem(listaDoRebelde2, "água") - qtdDeCadaItem(listaDoRebelde1, "água")));
            rebelde1.getRecursos().setMunicao(Math.toIntExact(rebelde1.getRecursos().getMunicao() + qtdDeCadaItem(listaDoRebelde2, "municão") - qtdDeCadaItem(listaDoRebelde1, "munição")));
            rebelde1.getRecursos().setComida(Math.toIntExact(rebelde1.getRecursos().getComida() + qtdDeCadaItem(listaDoRebelde2, "comida") - qtdDeCadaItem(listaDoRebelde1, "comida")));
            rebelde1.getRecursos().setArma(Math.toIntExact(rebelde1.getRecursos().getArma() + qtdDeCadaItem(listaDoRebelde2, "arma") - qtdDeCadaItem(listaDoRebelde1, "arma")));

            //================ (+) INVENTÁRIO REBELDES 2
            rebelde2.getRecursos().setAgua(Math.toIntExact(rebelde2.getRecursos().getAgua() + qtdDeCadaItem(listaDoRebelde1, "água") - qtdDeCadaItem(listaDoRebelde2,"água")));
            rebelde2.getRecursos().setArma(Math.toIntExact(rebelde2.getRecursos().getArma() + qtdDeCadaItem(listaDoRebelde1, "arma") - qtdDeCadaItem(listaDoRebelde2, "arma")));
            rebelde2.getRecursos().setMunicao(Math.toIntExact(rebelde2.getRecursos().getMunicao() + qtdDeCadaItem(listaDoRebelde1, "munição") - qtdDeCadaItem(listaDoRebelde2, "munição")));
            rebelde2.getRecursos().setComida(Math.toIntExact(rebelde2.getRecursos().getComida() + qtdDeCadaItem(listaDoRebelde1, "comida") - qtdDeCadaItem(listaDoRebelde2,"comida")));

            return true;
        }
        return false;
    }

    public long qtdDeCadaItem(List<String> listDoRebelde, String itemDeVerificacao){
        return  listDoRebelde.stream().filter(item -> item.equalsIgnoreCase(itemDeVerificacao)).count();
    }
     public boolean verificacaoDeValidadeDaLista(Rebelde rebelde, List<String> listaDoRebelde){
        boolean verificacaoArma = rebelde.getRecursos().getArma() < (qtdDeCadaItem(listaDoRebelde, "arma"));
        boolean verificaoMunicao = rebelde.getRecursos().getMunicao() < qtdDeCadaItem(listaDoRebelde, "municao");
        boolean verificacoAgua = rebelde.getRecursos().getAgua() < (qtdDeCadaItem(listaDoRebelde,"água"));
        boolean verificacaoComida = rebelde.getRecursos().getComida() < qtdDeCadaItem(listaDoRebelde, "comida");
        if(verificacaoArma && verificaoMunicao && verificacoAgua && verificacaoComida){
            return true;
        }
        return false;
     }

     //TODO TESTAR O CÓDIGO
}
