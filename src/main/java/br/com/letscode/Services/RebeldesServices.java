package br.com.letscode.Services;


import br.com.letscode.DAO.RebeldesDAO;
import br.com.letscode.Model.Localizacao;
import br.com.letscode.Model.Rebelde;
import br.com.letscode.Model.Recursos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class RebeldesServices {

    private RebeldesDAO rebeldesDAO;

    @Autowired
    public RebeldesServices(RebeldesDAO rebeldesDAO){
        this.rebeldesDAO = rebeldesDAO;
    }
    public List<Rebelde> listarRebeldes(){
        return rebeldesDAO.lerArquivo();
    }

    public Rebelde criarRebelde(Rebelde novoRebelde) {
        return rebeldesDAO.persistirRebelde(novoRebelde);
    }

    public Float getPorcentagemTraidores(){
        return rebeldesDAO.getPorcentagemTraidores();
    }

    public Float getPorcentagemRebeldes() {
        return rebeldesDAO.getPorcentagemRebeldes();
    }

    public List<Integer> getQuantidadeMedia() {
        return rebeldesDAO.getQuantidadeMedia();
    }

    public String reportarRebelde(String nomeReportado) throws IOException {
        return rebeldesDAO.fazerReport(nomeReportado);
    }

    public Integer getQuantidadePontos(){
        return rebeldesDAO.calculoPontosPerdidosRebeldes();
    }

    public String atualizarLocalizacao(Localizacao novaLocalizacao, String nomeRebelde) {
        return rebeldesDAO.atualizarLocalizacao(novaLocalizacao,nomeRebelde);
    }

    public String atualizarIventario(Recursos recursos, String nomeRebelde){
        return rebeldesDAO.atualizarIventario(recursos,nomeRebelde);
    }
}
