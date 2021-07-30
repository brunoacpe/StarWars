package br.com.letscode.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Rebelde {

    private String nome;
    private String genero;
    private Localizacao localizacao;
    private Recursos recursos;
    private Integer reports = 0;
    private boolean traitor;


    public static List<Integer> getRecursosArmas(List<Rebelde> rebeldeList){
        return rebeldeList
                .stream()
                .map(Rebelde::getRecursos)
                .map(Recursos::getArma)
                .collect(Collectors.toList());
    }

    public static List<Integer> getRecursosMunicao(List<Rebelde> rebeldeList){
        return rebeldeList
                .stream()
                .map(Rebelde::getRecursos)
                .map(Recursos::getMunicao)
                .collect(Collectors.toList());
    }

    public static List<Integer> getRecursosComida(List<Rebelde> rebeldeList){
        return rebeldeList
                .stream()
                .map(Rebelde::getRecursos)
                .map(Recursos::getComida)
                .collect(Collectors.toList());
    }

    public static List<Integer> getRecursosAgua(List<Rebelde> rebeldeList){
        return rebeldeList
                .stream()
                .map(Rebelde::getRecursos)
                .map(Recursos::getAgua)
                .collect(Collectors.toList());
    }
}
