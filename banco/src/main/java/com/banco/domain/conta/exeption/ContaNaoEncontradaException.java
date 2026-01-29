package com.banco.domain.conta.exeption;

public class ContaNaoEncontradaException extends RuntimeException {
    private static final String MENSAGEM_PADRAO = "Conta de número %d não Encontrada!";

    public ContaNaoEncontradaException(long numeroConta) {
        super(String.format(MENSAGEM_PADRAO, numeroConta));
    }

}
