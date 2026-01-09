package com.banco.domain.conta.model;

import com.banco.domain.conta.cliente.model.Cliente;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor

public class Conta {


    public Conta(Cliente cliente,BigDecimal saldo ) {
        this.cliente = cliente;
        this.saldo = saldo;
        this.ativo = true;

    }

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

        saldo = saldo.add(valor);
    }

    public void desativar() {
        this.ativo = false;


    }

    public void ativar() {
        this.ativo = true;
    }

    @Override
    public String toString() {
        return "Conta{" +
                "numero=" + numero +
                ", cliente=" + cliente +
                ", saldo=" + saldo +
                ", ativo=" + ativo +
                '}';
    }
}
