package com.banco.application.service;

import com.banco.domain.conta.model.Conta;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContaService {

    private List<Conta> contas;

    public ContaService() {
        this.contas = new ArrayList<Conta>();
    }


    public Conta cadastrar(Conta conta) {
        contas.add(conta);
        return conta;
    }

    public Conta depositar(int numeroConta, BigDecimal valor) {
        Conta conta = buscar(numeroConta);
        conta.depositar(valor);
        return conta;

    }

    public Conta sacar(int numeroConta, BigDecimal valor) {
        Conta conta = buscar(numeroConta);
        conta.sacar(valor);
        return conta;

    }

    public void trasnferir(int numeroContaRemetente, int numeroContaDestinatario, BigDecimal valor) {
        Conta remetente = buscar(numeroContaRemetente);
        Conta destinatario = buscar(numeroContaDestinatario);

    }

    private void validarTransferencia(Conta remetente, Conta destinatario , BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            // Throw

        }
        if (remetente.getSaldo().compareTo(valor) < 0) {
            // Throw

        }

    }


    public Conta alterarAtivo(int numeroConta) {

    }

    public List<Conta> buscarTodos() {
        return this.contas;
    }

    public Conta buscar(int numeroConta) {
        for (Conta conta : contas) {
            if (conta.getNumero() == numeroConta) {
                return conta;
            }
        }
        throw new RuntimeException("a");
    }

}
