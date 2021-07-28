package br.com.letscode.Model;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Recursos {

    private Integer arma;
    private Integer municao;
    private Integer agua;
    private Integer comida;

    public Integer getArma() {
        return this.arma;
    }

    public Integer getMunicao() {
        return this.municao;
    }

    public Integer getAgua() {
        return this.agua;
    }

    public Integer getComida() {
        return this.comida;
    }
}
