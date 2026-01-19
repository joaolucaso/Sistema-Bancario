package com.banco.domain.conta.exeption;

public class ContaInativaException extends RuntimeException {

    private static final String  MENSAGEM_PADRAO = "Conta de número %d não está ativa para receber operações!";


    public ContaInativaException(int numeroConta) {

        super(String.format(MENSAGEM_PADRAO, numeroConta));
    }
}
