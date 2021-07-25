package br.com.letscode.DAO;


import br.com.letscode.Model.Localizacao;
import br.com.letscode.Model.Rebelde;
import br.com.letscode.Model.Recursos;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
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

    public List<Rebelde> lerArquivo() {

        List<Rebelde> rebeldesList = new ArrayList<>();
        try(var br = Files.newBufferedReader(pathRebeldes)){
            rebeldesList = br.lines().map(this::converterLinhaEmRebelde)
                    .collect(Collectors.toList());
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
        List<Rebelde> rebeldeList = lerArquivo();
        List<Rebelde> traidoresList =  rebeldeList
                .stream()
                .filter(Rebelde::isTraitor)
                .collect(Collectors.toList());
        if(traidoresList.isEmpty()){
            //todo -- retornar erro que diz que não existem traidores na galaxia.
        }
        //TODO -- CALCULO DE PORCENTAGEM DE TRAIDORES >>
        float porcentagemTraidores = (traidoresList.size()*100)/(rebeldeList.size()*100);
        return porcentagemTraidores;
    }

    public float getPorcentagemRebeldes(){
        List<Rebelde> rebeldeList = lerArquivo();
        List<Rebelde> realmenteRebeldeList = rebeldeList
                .stream()
                .filter(r -> !(r.isTraitor()))
                .collect(Collectors.toList());
        //TODO --- CALCULO DE PORCENTAGEM DE REBELDES NA LISTA;

        int numeroTotal = rebeldeList.size()*100;
        float numeroTraidores = getPorcentagemTraidores();
        return numeroTotal-numeroTraidores;
    }

    public List<Integer> getQuantidadeMedia() {

        List<Rebelde> rebeldeList = lerArquivo();

        List<Rebelde> listaSemTraidores = rebeldeList
                .stream()
                .filter(t -> !(t.isTraitor()))
                .collect(Collectors.toList());

        List<Integer> listArmas = listaSemTraidores
                .stream()
                .map(Rebelde::getRecursos)
                .map(Recursos::getArma)
                .collect(Collectors.toList());

        var armas = somarListFazerMedia(listArmas);

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

    public void apagarArquivo() throws IOException {
        Files.delete(pathRebeldes);
    }
    public void escreverListaNoArquivo(List<Rebelde> rebeldeList){
        try{
            PrintWriter printWriter = new PrintWriter("src\\main\\java\\br\\com\\letscode\\Files\\rebeldes.txt", StandardCharsets.UTF_8);
            printWriter.close();
        } catch (IOException e){
            e.printStackTrace();
        }

        for(Rebelde r: rebeldeList){
            persistirRebelde(r);
        }
    }

    public String fazerReport(String nomeReportado) throws IOException {
        List<Rebelde> rebeldeList = lerArquivo();

        for(Rebelde r: rebeldeList){
            if(r.getNome().equalsIgnoreCase(nomeReportado)&&r.isTraitor()){
                return "Já temos a informação de que este rebelde se tornou um traidor.";
            }
            if (r.getNome().equals(nomeReportado)&&!(r.isTraitor())){
                r.setReports(r.getReports()+1);
                if(r.getReports()==3){
                    r.setTraitor(true);
                    apagarArquivo();
                    escreverListaNoArquivo(rebeldeList);
                    return "Este rebelde recebeu 3 reports. A partir de hoje ele é considerado um traidor !";
                }
            }

        }

        apagarArquivo();
        escreverListaNoArquivo(rebeldeList);
        return "Traidor reportado!!! Obrigado por contribuir por uma galaxia melhor.";
    }

    public Integer calculoPontosRebeldes() {
        List<Recursos> listaRecursosRebeldes = lerArquivo()
                .stream()
                .filter(t -> !(t.isTraitor()))
                .map(Rebelde::getRecursos)
                .collect(Collectors.toList());
        var valorComida = 1;
        var valorAgua = 2;
        var valorArma = 4;
        var valorMunicao =3;

        List<Integer> listAgua =listaRecursosRebeldes
                .stream()
                .map(Recursos::getAgua)
                .collect(Collectors.toList());
        Integer pontosAgua = somarList(listAgua)*valorAgua;

        List<Integer> listComida =listaRecursosRebeldes
                .stream()
                .map(Recursos::getComida)
                .collect(Collectors.toList());
        Integer pontosComida = somarList(listComida)*valorComida;

        List<Integer> listArma =listaRecursosRebeldes
                .stream()
                .map(Recursos::getArma)
                .collect(Collectors.toList());
        Integer pontosArma = somarList(listArma)*valorArma;

        List<Integer> listMunicao =listaRecursosRebeldes
                .stream()
                .map(Recursos::getMunicao)
                .collect(Collectors.toList());
        Integer pontosMunicao = somarList(listMunicao)*valorMunicao;

        return pontosComida+pontosArma+pontosMunicao+pontosAgua;
    }

    public Integer somarList(List<Integer> x){
        var soma = 0;
        for(Integer y: x){
            soma+=y;
        }
        return soma;
    }

    public String atualizarLocalizacao(Localizacao novaLocalizacao, String nomeRebelde) {
        List<Rebelde> listSemTraidores = lerArquivo()
                .stream()
                .filter(r -> !(r.isTraitor()))
                .collect(Collectors.toList());
        if(listSemTraidores.stream().filter(n -> n.getNome().equalsIgnoreCase(nomeRebelde)).findAny().isEmpty()){
            return "Não existe nenhum rebelde com este nome.";
        }
        for(Rebelde r: listSemTraidores){
            if(r.getNome().equalsIgnoreCase(nomeRebelde)){
                r.setLocalizacao(novaLocalizacao);
            }
        }
        try{
            apagarArquivo();
        } catch (IOException ex){
            ex.getMessage();
        }
        escreverListaNoArquivo(listSemTraidores);
        return "Localização atualizada !!! Bom saber por onde você anda.";

    }
}
