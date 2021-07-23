package br.com.letscode.DAO;


import br.com.letscode.Model.Rebelde;
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
        novoRebelde.setNome(st.nextToken());
        novoRebelde.setGenero(st.nextToken());
        novoRebelde.setNomeGalaxia(st.nextToken());
        novoRebelde.setLatitude(Long.parseLong(st.nextToken()));
        novoRebelde.setLongitude(Long.parseLong(st.nextToken()));
        novoRebelde.setArma(Integer.parseInt(st.nextToken()));
        novoRebelde.setMunicao(Integer.parseInt(st.nextToken()));
        novoRebelde.setComida(Integer.parseInt(st.nextToken()));
        novoRebelde.setAgua(Integer.parseInt(st.nextToken()));
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
        return String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\r\n",novoRebelde.getNome(),novoRebelde.getGenero(),novoRebelde.getNomeGalaxia(),novoRebelde.getLatitude(),novoRebelde.getLongitude(),novoRebelde.getArma(),novoRebelde.getMunicao(),novoRebelde.getComida(),novoRebelde.getAgua(),novoRebelde.isTraitor());
    }

    public int getPorcentagemTraidores() {
        List<Rebelde> rebeldeList = listarTodosRebeldes();
        List<Rebelde> traidoresList =  rebeldeList
                .stream()
                .filter(Rebelde::isTraitor)
                .collect(Collectors.toList());
        if(traidoresList.isEmpty()){
            //todo -- retornar erro que diz que não existem traidores na galaxia.
        }
        int porcentagemTraidores = (traidoresList.size()*100)/(rebeldeList.size()*100);
        return porcentagemTraidores;
    }

    public int getPorcentagemRebeldes(){
        List<Rebelde> rebeldeList = listarTodosRebeldes();
        int numeroTotal = rebeldeList.size()*100;
        int numeroTraidores = getPorcentagemTraidores();
        return numeroTotal-numeroTraidores;
    }

    public int getQuantidadeMedia() {
        List<Rebelde> rebeldeList = listarTodosRebeldes();

        List<Rebelde> listaSemTraidores = rebeldeList
                .stream()
                .filter(t -> !(t.isTraitor()))
                .collect(Collectors.toList());

        List<Integer> listArmas = listaSemTraidores
                .stream()
                .map(Rebelde::getArma)
                .collect(Collectors.toList());
        somarListFazerMedia(listArmas);

        List<Integer> listMunicao = listaSemTraidores
                .stream()
                .map(Rebelde::getMunicao)
                .collect(Collectors.toList());
        somarListFazerMedia(listMunicao);

        List<Integer> listComida = listaSemTraidores
                .stream()
                .map(Rebelde::getComida)
                .collect(Collectors.toList());
        somarListFazerMedia(listComida);

        List<Integer> listAgua = listaSemTraidores
                .stream()
                .map(Rebelde::getAgua)
                .collect(Collectors.toList());
        somarListFazerMedia(listAgua);
        //TODO -- AINDA ARRUMAR ESTE MÉTODO.
        return 0;
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
