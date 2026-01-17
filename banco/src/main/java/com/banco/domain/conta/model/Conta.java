package com.banco.domain.conta.model;

import com.banco.domain.conta.cliente.model.Cliente;
import com.banco.domain.conta.historico.model.Historico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.LinkedList;

@Getter
@Setter
@AllArgsConstructor

public class Conta {


    private int numero;
    private Cliente cliente;
    private BigDecimal saldo;
    private LinkedList<Historico> historicos;
    private boolean ativo;


    public Conta(Cliente cliente, BigDecimal saldo) {
        this.cliente = cliente;
        this.saldo = saldo;
        this.ativo = true;

    }

    public void sacar(BigDecimal valor) {

        this.saldo = saldo.subtract(valor);
    }

    public void depositar(BigDecimal valor) {

        this.saldo = saldo.add(valor);
    }

    public void ativar() {
        this.ativo = true;
    }

    public void desativar() {
        this.ativo = false;

    }

    public void addHistorico(Historico historico) {
        if (historicos == null) {
            historicos = new LinkedList<>();

        }

        this.historicos.add(historico);
    }

    @Override
    public String toString() {
        return "Conta{" +
                "numero=" + numero +
                ", cliente=" + cliente +
                ", saldo=" + saldo +
                ", historicos=" + Arrays.toString(historicos.toArray())  +
                ", ativo=" + ativo +
                '}';
    }
}
