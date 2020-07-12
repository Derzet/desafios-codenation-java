package br.com.codenation.desafio.exceptions;

public class IdentificadorUtilizadoException extends RuntimeException{
    public IdentificadorUtilizadoException(){
        super("O Identificador já está sendo utilizado!");
    }
}
