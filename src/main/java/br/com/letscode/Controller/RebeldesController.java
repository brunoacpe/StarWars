package br.com.letscode.Controller;


import br.com.letscode.DAO.RebeldesDAO;
import br.com.letscode.Model.Rebelde;
import br.com.letscode.Services.RebeldesServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RequestMapping("/rebeldes")
@RestController
public class RebeldesController {

    private RebeldesServices rebeldesServices;

    @Autowired
    public RebeldesController(RebeldesServices rebeldesServices){
        this.rebeldesServices = rebeldesServices;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Rebelde> listarRebeldes(){
        return this.rebeldesServices.listarRebeldes();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Rebelde criarRebelde(@RequestBody Rebelde rebelde) {
        var novoRebelde = new Rebelde();
        novoRebelde.setNome(rebelde.getNome());
        novoRebelde.setGenero(rebelde.getGenero());
        novoRebelde.setLatitude(rebelde.getLatitude());
        novoRebelde.setLongitude(rebelde.getLongitude());
        novoRebelde.setNomeGalaxia(rebelde.getNomeGalaxia());
        novoRebelde.setArma(rebelde.getArma());
        novoRebelde.setMunicao(rebelde.getMunicao());
        novoRebelde.setComida(rebelde.getComida());
        novoRebelde.setAgua(rebelde.getAgua());
        novoRebelde.setTraitor(false);
        return rebeldesServices.criarRebelde(novoRebelde);
    }
}
