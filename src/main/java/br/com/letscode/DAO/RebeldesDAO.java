package br.com.letscode.DAO;


import br.com.letscode.Model.Localizacao;
import br.com.letscode.Model.Rebelde;
import br.com.letscode.Model.Recursos;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Component
public class RebeldesDAO {

    private static final String  caminho = "src\\main\\resources\\data\\rebeldes.txt";
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
            log.error("Não foi possivel acessar o arquivo.");
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
        novoRebelde.getLocalizacao().setLatitude(Float.parseFloat(st.nextToken()));
        novoRebelde.getLocalizacao().setLongitude(Float.parseFloat(st.nextToken()));
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
            log.info("O novo rebelde {} foi cadastrado.",novoRebelde.getNome());
        } catch (IOException e) {
            log.error("Não foi possivel acessar o arquivo.");
            e.printStackTrace();
        }
        return novoRebelde;

    }

    private String formatar(Rebelde novoRebelde) {
        return String.format("%s;%s;%s;%s;%s;%s;%s;%s;%s;%s;%s\r\n",novoRebelde.getNome(),novoRebelde.getGenero(),novoRebelde.getLocalizacao().getNomeGalaxia(),novoRebelde.getLocalizacao().getLatitude().toString(),novoRebelde.getLocalizacao().getLongitude().toString(),novoRebelde.getRecursos().getArma(),novoRebelde.getRecursos().getMunicao(),novoRebelde.getRecursos().getComida(),novoRebelde.getRecursos().getAgua(),novoRebelde.getReports(),novoRebelde.isTraitor());
    }

    public Float getPorcentagemTraidores() {
        List<Rebelde> rebeldeList = lerArquivo();
        List<Rebelde> traidoresList =  rebeldeList
                .stream()
                .filter(Rebelde::isTraitor)
                .collect(Collectors.toList());

        int numTraidores = traidoresList.size();
        int total = rebeldeList.size();

        int porcentagemTraidores = (numTraidores * 100) / total;

        return Float.valueOf(porcentagemTraidores);
    }

    public Float getPorcentagemRebeldes() {
        Float porcentagemRebeldes = 100 - getPorcentagemTraidores();
        return porcentagemRebeldes;
    }

    public List<Rebelde> filtrarTraidores(){
        List<Rebelde> list = lerArquivo();
        return list.stream().filter(t -> !(t.isTraitor())).collect(Collectors.toList());
    }

    public List<Rebelde> filtrarRebeldes(){
        List<Rebelde> list = lerArquivo();
        return list.stream().filter(Rebelde::isTraitor).collect(Collectors.toList());
    }

    public List<Integer> getQuantidadeMedia() {

        List<Rebelde> listaSemTraidores = filtrarTraidores();

        List<Integer> listArmas = Rebelde.getRecursosArmas(listaSemTraidores);
        var armas = somarListFazerMedia(listArmas);

        List<Integer> listMunicao = Rebelde.getRecursosMunicao(listaSemTraidores);
        var municao =  somarListFazerMedia(listMunicao);

        List<Integer> listComida = Rebelde.getRecursosComida(listaSemTraidores);
        var comida = somarListFazerMedia(listComida);

        List<Integer> listAgua = Rebelde.getRecursosAgua(listaSemTraidores);
        var agua = somarListFazerMedia(listAgua);

        return List.of(armas,municao,comida,agua);
    }

    public Integer somarListFazerMedia(List<Integer> x){

        int tamanho = x.size();
        int soma = 0;
        for(int i : x){
            soma+=i;
        }
        return soma/tamanho;
    }

    public void escreverListaNoArquivo(List<Rebelde> rebeldeList){
        try{
            Files.delete(pathRebeldes);
        } catch(IOException ex){
            log.error("Não foi possivel acessar o arquivo.");
            ex.printStackTrace();
        }

        try{
            PrintWriter printWriter = new PrintWriter("src\\main\\resources\\data\\rebeldes.txt", StandardCharsets.UTF_8);
            printWriter.close();
        } catch (IOException e){
            log.error("Não foi possivel acessar o arquivo.");
            e.printStackTrace();
        }

        for(Rebelde r: rebeldeList){
            persistirRebelde(r);
        }
    }

    public String fazerReport(String nomeReportado) {
        List<Rebelde> rebeldeList = lerArquivo();

        for(Rebelde r: rebeldeList){
            if(r.getNome().equalsIgnoreCase(nomeReportado)&&r.isTraitor()){
                log.warn("Report em um rebelde que já é traidor.");
                return "Já temos a informação de que este rebelde se tornou um traidor.";
            }
            if (r.getNome().equals(nomeReportado)&&!(r.isTraitor())){
                r.setReports(r.getReports()+1);
                if(r.getReports()==3){
                    log.info("{} se tornou um traidor.",nomeReportado);
                    r.setTraitor(true);
                    escreverListaNoArquivo(rebeldeList);
                    return "Este rebelde recebeu 3 reports. A partir de hoje ele é considerado um traidor !";
                }
            }

        }

        escreverListaNoArquivo(rebeldeList);
        return "Traidor reportado!!! Obrigado por contribuir por uma galaxia melhor.";
    }

    public Integer calculoPontosPerdidosRebeldes() {
        List<Rebelde> listaTraidores = filtrarRebeldes();
        var valorComida = 1;
        var valorAgua = 2;
        var valorArma = 4;
        var valorMunicao =3;

        List<Integer> listAguaTraidores = Rebelde.getRecursosAgua(listaTraidores);
        Integer pontosAguaTraidores = somarList(listAguaTraidores)*valorAgua;

        List<Integer> listComidaTraidores = Rebelde.getRecursosComida(listaTraidores);
        Integer pontosComidaTraidores = somarList(listComidaTraidores)*valorComida;

        List<Integer> listArmaTraidores = Rebelde.getRecursosArmas(listaTraidores);
        Integer pontosArmaTraidores = somarList(listArmaTraidores)*valorArma;

        List<Integer> listMunicaoTraidores = Rebelde.getRecursosMunicao(listaTraidores);
        Integer pontosMunicaoTraidores = somarList(listMunicaoTraidores)*valorMunicao;

        return pontosAguaTraidores+pontosComidaTraidores+pontosArmaTraidores+pontosMunicaoTraidores;
    }

    public Integer somarList(List<Integer> x){
        var soma = 0;
        for(Integer y: x){
            soma+=y;
        }
        return soma;
    }

    public String atualizarLocalizacao(Localizacao novaLocalizacao, String nomeRebelde) {
        List<Rebelde> list = lerArquivo();
        if(list.stream().filter(n -> n.getNome().equalsIgnoreCase(nomeRebelde)).findAny().isEmpty()){
            return "Não existe nenhum rebelde com este nome.";
        }
        for(Rebelde r: list){
            if(r.getNome().equalsIgnoreCase(nomeRebelde)&&!(r.isTraitor())){
                r.setLocalizacao(novaLocalizacao);
                escreverListaNoArquivo(list);
                return "Localização atualizada !!! Bom saber por onde você anda.";
            }
        }
        return "Você não vai conseguir atualizar sua localização, traidor.";

    }

    public String atualizarIventario(Recursos recursos, String nomeRebelde) {
        List<Rebelde> list = lerArquivo();
        if(list.stream().filter(n -> n.getNome().equalsIgnoreCase(nomeRebelde)).findAny().isEmpty()){
            return "Não existe nenhum rebelde com este nome.";
        }
        for(Rebelde r: list){
            if(r.getNome().equalsIgnoreCase(nomeRebelde)&&!(r.isTraitor())){
                r.setRecursos(recursos);
                escreverListaNoArquivo(list);
                return "Recursos atualizados !!!";
            }
        }
        return "Você não vai conseguir atualizar seus recursos, traidor.";

    }
}
