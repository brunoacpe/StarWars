package br.com.letscode.Services;


import br.com.letscode.DAO.RebeldesDAO;
import br.com.letscode.Model.Rebelde;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RebeldesServices {

    private RebeldesDAO rebeldesDAO;

    public RebeldesServices(RebeldesDAO rebeldesDAO){
        rebeldesDAO = rebeldesDAO;
    }
    public List<Rebelde> listarRebeldes(){
        return rebeldesDAO.listarTodosRebeldes();
    }

    public Rebelde criarRebelde(Rebelde novoRebelde) {
        return rebeldesDAO.persistirRebelde(novoRebelde);
    }
}
