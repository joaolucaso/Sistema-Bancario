package com.banco.domain.conta.model;

import com.banco.domain.conta.cliente.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Conta {
    private int numero;
    private Cliente cliente;
    private BigDecimal saldo;
    private boolean ativo;


    public void sacar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            // Throw

        }
        if (saldo.compareTo(valor) < 0) {
            // Throw

        }
        saldo = saldo.subtract(valor);
    }

    public void depositar(BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            // Throw

        }

        saldo = saldo.add(valor);
    }

}
