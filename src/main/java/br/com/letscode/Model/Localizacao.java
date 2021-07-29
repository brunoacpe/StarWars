package br.com.letscode.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Localizacao {

    private String nomeGalaxia;
    private Float latitude;
    private Float longitude;

}
