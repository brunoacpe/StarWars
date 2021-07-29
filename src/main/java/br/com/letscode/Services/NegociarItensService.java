package br.com.letscode.Services;

import br.com.letscode.Model.ArrayDeRecursos;
import br.com.letscode.Model.Rebelde;
import br.com.letscode.Model.Recursos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class NegociarItensService {

    private RebeldesServices negociarItensService;
    @Autowired
    public NegociarItensService(RebeldesServices rebeldesServices){
        this.negociarItensService = rebeldesServices;
    }

    public String negociarItens(ArrayDeRecursos itensDeTrocaRebelde, String nomeRebelde1, String nomeRebelde2) {
        Recursos itensDeTrocaRebelde1 = itensDeTrocaRebelde.getRecursos1();
        Recursos itensDeTrocaRebelde2 = itensDeTrocaRebelde.getRecursos2();
        Optional<Rebelde> rebelde1 = negociarItensService.listarRebeldes().stream().filter(r -> r.getNome().equals(nomeRebelde1)).findFirst();
        Optional<Rebelde> rebelde2 = negociarItensService.listarRebeldes().stream().filter(r -> r.getNome().equals(nomeRebelde2)).findFirst();
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

    public long pontuacaoDosItens(Recursos itensDeTroca){
        long numeroDeArmas = itensDeTroca.getArma();
        long numeroDeMunicao = itensDeTroca.getMunicao();
        long numeroDeAguas = itensDeTroca.getAgua();
        long numeroDeComida = itensDeTroca.getComida();
        return (numeroDeArmas * 4) + (numeroDeMunicao * 3) + ( numeroDeAguas * 2) + ( numeroDeComida) ;
    }

    public boolean trocaDeItens(Optional<Rebelde> rebelde1, Recursos listaDoRebelde1, Optional<Rebelde> rebelde2, Recursos listaDoRebelde2){
        if(verificacaoDeValidadeDaLista(rebelde1, listaDoRebelde1) && verificacaoDeValidadeDaLista(rebelde2, listaDoRebelde2)){
            //================= (+) INVENTÁRIO REBELDES 1
            rebelde1.get().getRecursos().setAgua(Math.toIntExact(rebelde1.get().getRecursos().getAgua() + listaDoRebelde2.getAgua() - listaDoRebelde1.getAgua()));
            rebelde1.get().getRecursos().setMunicao(Math.toIntExact(rebelde1.get().getRecursos().getMunicao() + listaDoRebelde2.getMunicao() - listaDoRebelde1.getMunicao()));
            rebelde1.get().getRecursos().setComida(Math.toIntExact(rebelde1.get().getRecursos().getComida() + listaDoRebelde2.getComida() - listaDoRebelde1.getComida()));
            rebelde1.get().getRecursos().setArma(Math.toIntExact(rebelde1.get().getRecursos().getArma() + listaDoRebelde2.getArma() - listaDoRebelde1.getArma()));
            negociarItensService.atualizarIventario(rebelde1.get().getRecursos(),rebelde1.get().getNome());

            //================ (+) INVENTÁRIO REBELDES 2
            rebelde2.get().getRecursos().setAgua(Math.toIntExact(rebelde2.get().getRecursos().getAgua() + listaDoRebelde1.getAgua() - listaDoRebelde2.getAgua()));
            rebelde2.get().getRecursos().setArma(Math.toIntExact(rebelde2.get().getRecursos().getArma() + listaDoRebelde1.getArma() - listaDoRebelde2.getArma()));
            rebelde2.get().getRecursos().setMunicao(Math.toIntExact(rebelde2.get().getRecursos().getMunicao() + listaDoRebelde1.getMunicao() - listaDoRebelde2.getMunicao()));
            rebelde2.get().getRecursos().setComida(Math.toIntExact(rebelde2.get().getRecursos().getComida() + listaDoRebelde1.getComida() - listaDoRebelde2.getComida()));
            negociarItensService.atualizarIventario(rebelde2.get().getRecursos(),rebelde2.get().getNome());

            return true;
        }
        return false;
    }

    public boolean verificacaoDeValidadeDaLista(Optional<Rebelde> rebelde, Recursos listaDoRebelde){
        boolean verificacaoArma = rebelde.get().getRecursos().getArma() >= listaDoRebelde.getArma();
        boolean verificaoMunicao = rebelde.get().getRecursos().getMunicao() >= listaDoRebelde.getMunicao();
        boolean verificacoAgua = rebelde.get().getRecursos().getAgua() >= listaDoRebelde.getAgua();
        boolean verificacaoComida = rebelde.get().getRecursos().getComida() >= listaDoRebelde.getComida();
        return verificacaoArma && verificaoMunicao && verificacoAgua && verificacaoComida;
    }
}
