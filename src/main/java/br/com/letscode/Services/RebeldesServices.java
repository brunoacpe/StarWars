package br.com.letscode.Services;


import br.com.letscode.DAO.RebeldesDAO;
import br.com.letscode.Model.Rebelde;
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

    public float getPorcentagemTraidores(){
        return rebeldesDAO.getPorcentagemTraidores();
    }

    public float getPorcentagemRebeldes() {
        return rebeldesDAO.getPorcentagemRebeldes();
    }

    public List<Integer> getQuantidadeMedia() {
        return rebeldesDAO.getQuantidadeMedia();
    }

    public String reportarRebelde(String nomeReportador, String nomeReportado) throws IOException {
        return rebeldesDAO.fazerReport(nomeReportado);
    }

    public Integer getQuantidadePontos(){
        return rebeldesDAO.calculoPontosRebeldes();
    }
}
