package com.banco.application.service;

import com.banco.domain.conta.exeption.ContaInativaException;
import com.banco.domain.conta.exeption.ContaNaoEncontradaException;
import com.banco.domain.conta.exeption.SaldoInsulficienteException;
import com.banco.domain.conta.exeption.ValorInvalidoException;
import com.banco.domain.conta.historico.model.Historico;
import com.banco.domain.conta.model.Conta;
import com.banco.infrastructure.utils.LogBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ContaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContaService.class);

    private List<Conta> contas;

    public ContaService() {
        this.contas = new ArrayList<>();
    }


    public Conta cadastrar(Conta conta) {
        LOGGER.info(LogBuilder.of()
                .header("iniciando cadastro da conta")
                .row("conta", conta)
                .build());

        conta.setNumero(contas.size() + 1);
        Historico.cadastro(conta);
        contas.add(conta);
        return conta;
    }

    public Conta depositar(int numeroConta, BigDecimal valor) {
        Conta conta = buscar(numeroConta);
        validarDeposito(conta, valor);
        Historico.deposito(conta, valor);
        conta.depositar(valor);
        return conta;

    }

    public Conta sacar(int numeroConta, BigDecimal valor) {
        Conta conta = buscar(numeroConta);
        validarSaque(conta, valor);
        Historico.saque(conta, valor);
        conta.sacar(valor);
        return conta;

    }

    public Conta trasnferir(int numeroContaRemetente,
                            int numeroContaDestinatario,
                            BigDecimal valor) {

        Conta remetente = buscar(numeroContaRemetente);
        Conta destinatario = buscar(numeroContaDestinatario);
        validarTransferencia(remetente, destinatario, valor);
        remetente.sacar(valor);
        destinatario.depositar(valor);
        Historico.transferencia(remetente, destinatario, valor);

        return remetente;
    }


    public Conta alterarAtivo(int numeroConta) {
        Conta conta = buscar(numeroConta);
        if (conta.isAtivo()) {
            conta.desativar();
        } else {
            conta.ativar();
        }
        Historico.alterarAtivo(conta);
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
        if (!conta.isAtivo()) {
            throw new ContaInativaException(conta.getNumero());

        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException(valor);

        }
        if (conta.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsulficienteException(conta.getSaldo(), valor);

        }


    }

    private void validarDeposito(Conta conta, BigDecimal valor) {
        if (!conta.isAtivo()) {

            throw new ContaInativaException(conta.getNumero());
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException(valor);

        }
    }

    private void validarTransferencia(Conta remetente,
                                      Conta destinatario,
                                      BigDecimal valor) {

        if (!remetente.isAtivo()) {
            throw new ContaInativaException(remetente.getNumero());
        }
        if (!destinatario.isAtivo()) {
            throw new ContaInativaException(destinatario.getNumero());
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException(valor);
        }
        if (remetente.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsulficienteException(remetente.getSaldo(), valor);

        }

    }
}
