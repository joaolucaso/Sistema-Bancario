package com.banco.domain.conta.exeption;

import java.math.BigDecimal;

public class ValorInvalidoException extends RuntimeException {
    private static final String MENSAGEM_PADRAO = "O valor de %s é inválido!";

    public ValorInvalidoException(BigDecimal valor) {
        super(String.format(MENSAGEM_PADRAO, valor));
    }

}
