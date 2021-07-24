package br.com.letscode.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    private boolean isTraitor;

}
