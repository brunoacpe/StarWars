package br.com.letscode.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Rebelde {

    private String nome;
    private String genero;
    private String nomeGalaxia;
    private Long latitude;
    private Long longitude;
    private List<String> inventario;
    private boolean isTraitor;

}
