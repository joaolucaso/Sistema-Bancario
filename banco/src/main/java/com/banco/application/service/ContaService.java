package com.banco.application.service;

import com.banco.domain.conta.exeption.ContaNaoEncontradaException;
import com.banco.domain.conta.exeption.SaldoInsulficienteException;
import com.banco.domain.conta.exeption.ValorInvalidoException;
import com.banco.domain.conta.model.Conta;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.List;

@Service
public class ContaService {

    private List<Conta> contas;

    public ContaService() {
        this.contas = new ArrayList<Conta>();
    }


    public Conta cadastrar(Conta conta) {
        conta.setNumero(contas.size()+1);
        contas.add(conta);
        return conta;
    }

    public Conta depositar(int numeroConta, BigDecimal valor) {
        Conta conta = buscar(numeroConta);
        validarDeposito(conta, valor);
        conta.depositar(valor);
        return conta;

    }

    public Conta sacar(int numeroConta, BigDecimal valor) {
        Conta conta = buscar(numeroConta);
        validarSaque(conta, valor);
        conta.sacar(valor);
        return conta;

    }

    public void trasnferir(int numeroContaRemetente, int numeroContaDestinatario, BigDecimal valor) {
        Conta remetente = buscar(numeroContaRemetente);
        Conta destinatario = buscar(numeroContaDestinatario);
        validarTransferencia(remetente, destinatario, valor);
        remetente.sacar(valor);
        destinatario.depositar(valor);


    }


    public Conta alterarAtivo(int numeroConta) {
        Conta conta = buscar(numeroConta);
        if (conta.isAtivo()) {
            conta.desativar();
        } else {
            conta.ativar();
        }

        return conta;

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
        throw new ContaNaoEncontradaException(numeroConta);
    }

    private void validarSaque(Conta conta, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException(valor);

        }
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsulficienteException(conta.getSaldo(), valor);

        }


    }

    private void validarDeposito(Conta conta, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException(valor);

        }
    }

    private void validarTransferencia(Conta remetente, Conta destinatario, BigDecimal valor) {
        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException(valor);
        }
        if (remetente.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsulficienteException(remetente.getSaldo(), valor);

        }

    }
}
