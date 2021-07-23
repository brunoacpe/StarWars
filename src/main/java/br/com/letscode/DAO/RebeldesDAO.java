package br.com.letscode.DAO;


import br.com.letscode.Model.Localizacao;
import br.com.letscode.Model.Rebelde;
import br.com.letscode.Model.Recursos;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

@Component
public class RebeldesDAO {

    private String caminho = "src\\main\\java\\br\\com\\letscode\\Files\\rebeldes.txt";
    private Path pathRebeldes;
    @PostConstruct
    public void init(){
        pathRebeldes = Paths.get(caminho);
    }
    public List<Rebelde> listarTodosRebeldes() {

        List<Rebelde> rebeldesList = new ArrayList<>();
        try(var br = Files.newBufferedReader(pathRebeldes)){
            rebeldesList = br.lines().map(this::converterLinhaEmRebelde).collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rebeldesList;
    }
    public Rebelde converterLinhaEmRebelde(String linha){
        StringTokenizer st = new StringTokenizer(linha,";");
        var novoRebelde = new Rebelde();
        var novaLocalizacao = new Localizacao();
        var novosRecursos = new Recursos();
        novoRebelde.setLocalizacao(novaLocalizacao);
        novoRebelde.setRecursos(novosRecursos);
        novoRebelde.setNome(st.nextToken());
        novoRebelde.setGenero(st.nextToken());
        novoRebelde.getLocalizacao().setNomeGalaxia(st.nextToken());
        novoRebelde.getLocalizacao().setLatitude(Long.parseLong(st.nextToken()));
        novoRebelde.getLocalizacao().setLongitude(Long.parseLong(st.nextToken()));
        novoRebelde.getRecursos().setArma(Integer.parseInt(st.nextToken()));
        novoRebelde.getRecursos().setMunicao(Integer.parseInt(st.nextToken()));
        novoRebelde.getRecursos().setComida(Integer.parseInt(st.nextToken()));
        novoRebelde.getRecursos().setAgua(Integer.parseInt(st.nextToken()));
        novoRebelde.setReports(Integer.parseInt(st.nextToken()));
        novoRebelde.setTraitor(Boolean.parseBoolean(st.nextToken()));
        return novoRebelde;
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
        return String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\r\n",novoRebelde.getNome(),novoRebelde.getGenero(),novoRebelde.getLocalizacao().getNomeGalaxia(),novoRebelde.getLocalizacao().getLatitude(),novoRebelde.getLocalizacao().getLongitude(),novoRebelde.getRecursos().getArma(),novoRebelde.getRecursos().getMunicao(),novoRebelde.getRecursos().getComida(),novoRebelde.getRecursos().getAgua(),novoRebelde.getReports(),novoRebelde.isTraitor());
    }

    public float getPorcentagemTraidores() {
        List<Rebelde> rebeldeList = listarTodosRebeldes();
        List<Rebelde> traidoresList =  rebeldeList
                .stream()
                .filter(Rebelde::isTraitor)
                .collect(Collectors.toList());
        if(traidoresList.isEmpty()){
            //todo -- retornar erro que diz que não existem traidores na galaxia.
        }
        float porcentagemTraidores = (traidoresList.size()*100)/(rebeldeList.size()*100);
        return porcentagemTraidores;
    }

    public float getPorcentagemRebeldes(){
        List<Rebelde> rebeldeList = listarTodosRebeldes();
        int numeroTotal = rebeldeList.size()*100;
        float numeroTraidores = getPorcentagemTraidores();
        return numeroTotal-numeroTraidores;
    }

    public List<Integer> getQuantidadeMedia() {
        List<Rebelde> rebeldeList = listarTodosRebeldes();

        List<Rebelde> listaSemTraidores = rebeldeList
                .stream()
                .filter(t -> !(t.isTraitor()))
                .collect(Collectors.toList());

        List<Integer> listArmas = listaSemTraidores
                .stream()
                .map(Rebelde::getRecursos)
                .map(Recursos::getArma)
                .collect(Collectors.toList());
        var armas =somarListFazerMedia(listArmas);

        List<Integer> listMunicao = listaSemTraidores
                .stream()
                .map(Rebelde::getRecursos)
                .map(Recursos::getMunicao)
                .collect(Collectors.toList());
        var municao =  somarListFazerMedia(listMunicao);

        List<Integer> listComida = listaSemTraidores
                .stream()
                .map(Rebelde::getRecursos)
                .map(Recursos::getComida)
                .collect(Collectors.toList());
        var comida = somarListFazerMedia(listComida);

        List<Integer> listAgua = listaSemTraidores
                .stream()
                .map(Rebelde::getRecursos)
                .map(Recursos::getAgua)
                .collect(Collectors.toList());
        var agua = somarListFazerMedia(listAgua);
        //TODO -- AINDA ARRUMAR ESTE MÉTODO.
        List<Integer> list = List.of(armas,municao,comida,agua);
        return list;
    }

    public int somarListFazerMedia(List<Integer> x){

        int tamanho = x.size();
        int soma = 0;
        for(int i : x){
            soma+=i;
        }
        return soma/tamanho;
    }
}
