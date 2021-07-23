package br.com.letscode.Controller;


import br.com.letscode.DAO.RebeldesDAO;
import br.com.letscode.Model.Rebelde;
import br.com.letscode.Services.RebeldesServices;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rebeldes")
public class RebeldesController {

    private RebeldesServices rebeldesServices;

    public RebeldesController(RebeldesServices rebeldesServices){
        rebeldesServices = rebeldesServices;
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
        novoRebelde.setTraitor(false);
        return rebeldesServices.criarRebelde(novoRebelde);
    }
}
