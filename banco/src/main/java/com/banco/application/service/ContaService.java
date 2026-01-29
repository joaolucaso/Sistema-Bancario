package com.banco.application.service;

import com.banco.domain.conta.exeption.ContaInativaException;
import com.banco.domain.conta.exeption.ContaNaoEncontradaException;
import com.banco.domain.conta.exeption.SaldoInsulficienteException;
import com.banco.domain.conta.exeption.ValorInvalidoException;
import com.banco.domain.conta.historico.model.Historico;
import com.banco.domain.conta.model.Conta;
import com.banco.infrastructure.utils.LogBuilder;
import com.banco.infrastructure.utils.repository.conta.ContaRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContaService.class);

    private final ContaRepository contaRepository;

    public ContaService(ContaRepository contaRepository) {

        this.contaRepository = contaRepository;
    }

    @Transactional
    public Conta cadastrar(Conta conta) {
        LOGGER.info(LogBuilder.of()
                .header("iniciando cadastro da conta")
                .row("conta", conta)
                .build());
        conta.ativar();

        Historico.cadastro(conta);
        conta = contaRepository.save(conta);

        LOGGER.info(LogBuilder.of()
                .header("Finalizando cadastro da conta")
                .row("conta", conta)
                .build());

        return conta;
    }

    @Transactional
    public Conta depositar(int numeroConta, BigDecimal valor) {
        Conta conta = buscar(numeroConta);
        validarDeposito(conta, valor);
        Historico.deposito(conta, valor);
        conta.depositar(valor);
        return contaRepository.save(conta);

    }

    @Transactional

    public Conta sacar(long numeroConta, BigDecimal valor) {
        Conta conta = buscar(numeroConta);
        validarSaque(conta, valor);
        Historico.saque(conta, valor);
        conta.sacar(valor);
        return contaRepository.save(conta);

    }

    @Transactional
    public Conta trasnferir(int numeroContaRemetente,
                            int numeroContaDestinatario,
                            BigDecimal valor) {

        Conta remetente = buscar(numeroContaRemetente);
        Conta destinatario = buscar(numeroContaDestinatario);
        validarTransferencia(remetente, destinatario, valor);
        Historico.transferencia(remetente, destinatario, valor);

        remetente.sacar(valor);
        remetente = contaRepository.save(remetente);

        destinatario.depositar(valor);
        contaRepository.save(destinatario);


        return remetente;
    }

    @Transactional
    public Conta alterarAtivo(int numeroConta) {
        Conta conta = buscar(numeroConta);
        if (conta.isAtivo()) {
            conta.desativar();
        } else {
            conta.ativar();
        }
        Historico.alterarAtivo(conta);
        return contaRepository.save(conta);

    }

    @Transactional(readOnly = true)
    public List<Conta> buscarTodos() {
        return contaRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Conta buscar(long idConta) {
        Optional<Conta> ContOp = contaRepository.buscarPorId(idConta);
        if (ContOp.isEmpty()) {
            throw new ContaNaoEncontradaException(idConta);
        }
        return ContOp.get();
    }

    private void validarSaque(Conta conta, BigDecimal valor) {
        if (!conta.isAtivo()) {
            throw new ContaInativaException(conta.getId());

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

            throw new ContaInativaException(conta.getId());
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException(valor);

        }
    }

    private void validarTransferencia(Conta remetente,
                                      Conta destinatario,
                                      BigDecimal valor) {

        if (!remetente.isAtivo()) {
            throw new ContaInativaException(remetente.getId());
        }
        if (!destinatario.isAtivo()) {
            throw new ContaInativaException(destinatario.getId());
        }

        if (valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new ValorInvalidoException(valor);
        }
        if (remetente.getSaldo().compareTo(valor) < 0) {
            throw new SaldoInsulficienteException(remetente.getSaldo(), valor);

        }

    }
}
