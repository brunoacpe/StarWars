package br.com.letscode.Excecoes;

public class NaoHaTraidoresException extends Throwable {
    private String message;

    public  NaoHaTraidoresException(){
        this.message = "Não há traidores nesta galáxia! Ainda.";
    }
}
