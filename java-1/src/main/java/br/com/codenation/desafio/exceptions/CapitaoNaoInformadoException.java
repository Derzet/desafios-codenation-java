package br.com.codenation.desafio.exceptions;

public class CapitaoNaoInformadoException extends RuntimeException {

    public CapitaoNaoInformadoException(){
        super("Capitão Não Informado na base de dados!");
    }

}
