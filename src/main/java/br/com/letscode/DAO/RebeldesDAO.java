package br.com.letscode.DAO;


import br.com.letscode.Model.Rebelde;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Component
public class RebeldesDAO {

    private String caminho = "src\\main\\java\\br\\com\\letscode\\Files\\rebeldes.txt";
    private Path pathRebeldes;
    @PostConstruct
    public void init(){
        pathRebeldes = Paths.get(caminho);
    }
    public List<Rebelde> listarTodosRebeldes() {

        return null;
    }

    public Rebelde persistirRebelde(Rebelde novoRebelde) {

        try(var bf = Files.newBufferedWriter(pathRebeldes, StandardOpenOption.APPEND)){
            bf.write(formatar(novoRebelde));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return novoRebelde;

    }

    private String formatar(Rebelde novoRebelde) {
        //TODO -- ARRUMAR ESTE MÉTODO.
        return String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\r\n",novoRebelde.getNome(),novoRebelde.getGenero(),novoRebelde.getNomeGalaxia(),novoRebelde.getLatitude(),novoRebelde.getLongitude(),novoRebelde.getComida(),novoRebelde.getArma(),novoRebelde.getMunicao(),novoRebelde.getAgua(),novoRebelde.isTraitor());
    }
}
