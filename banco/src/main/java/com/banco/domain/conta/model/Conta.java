package com.banco.domain.conta.model;

import com.banco.domain.conta.cliente.model.Cliente;
import com.banco.domain.conta.historico.model.Historico;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "t_conta")

public class Conta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade =CascadeType.ALL)
    private Cliente cliente;

    private BigDecimal saldo;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Historico> historicos = new ArrayList<>();

    private boolean ativo;


    public Conta(Cliente cliente, BigDecimal saldo) {
        this.cliente = cliente;
        this.saldo = saldo;
        this.ativo = true;
        this.historicos = new LinkedList<>();
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

    public Historico[] getHistoricos() {
        return historicos.toArray(new Historico[0]);
    }

    @Override
    public String toString() {
        return "Conta{" +
                "id=" + id +
                ", cliente=" + cliente +
                ", saldo=" + saldo +
                ", historicos=" + Arrays.toString(historicos.toArray()) +
                ", ativo=" + ativo +
                '}';
    }
}
