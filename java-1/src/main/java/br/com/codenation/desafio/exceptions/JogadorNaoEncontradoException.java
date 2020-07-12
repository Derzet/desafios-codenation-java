package br.com.codenation.desafio.exceptions;

public class JogadorNaoEncontradoException extends RuntimeException{
    public JogadorNaoEncontradoException(){
        super("Jogador não encontrado!");
    }
}
