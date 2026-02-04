package com.banco.domain.conta.controller;

import com.banco.application.service.ContaService;
import com.banco.domain.conta.exeption.ContaNaoEncontradaException;
import com.banco.domain.conta.model.Conta;
import com.banco.presentation.conta.resquest.ContaRequest;
import com.banco.presentation.conta.resquest.DepositoRequest;
import com.banco.presentation.conta.resquest.SaqueRequest;
import com.banco.presentation.conta.resquest.TransferenciaRequest;
import com.banco.presentation.conta.resquest.response.ContaResponse;

import com.banco.presentation.conta.resquest.response.ContaResponseReduzido;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/api/conta/")
public class ContaController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ContaService.class);


    private final ContaService contaService;
    private final ModelMapper modelMapper;

    public ContaController(ContaService contaService, final ModelMapper modelMapper) {
        this.contaService = contaService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("cadastrar")
    public ResponseEntity<ContaResponse> cadastrar(@RequestBody ContaRequest contaRequest) {

        Conta conta = modelMapper.map(contaRequest, Conta.class);
        conta = contaService.cadastrar(conta);
        ContaResponse response = modelMapper.map(conta, ContaResponse.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }

    @PostMapping("depositar")
    public ResponseEntity<?> depositar(@RequestBody DepositoRequest depositoRequest) {
        Conta conta = null;

        try {
            conta = contaService.depositar(depositoRequest.getIdConta(),
                    depositoRequest.getValor());
        } catch (ContaNaoEncontradaException contaNaoEncontradaException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(contaNaoEncontradaException.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        ContaResponse response = modelMapper.map(conta, ContaResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("sacar")
    public ResponseEntity<?> sacar(@RequestBody SaqueRequest saqueRequest) {
        Conta conta = null;

        try {
            conta = contaService.depositar(saqueRequest.getIdConta(),
                    saqueRequest.getValor());
        } catch (ContaNaoEncontradaException contaNaoEncontradaException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(contaNaoEncontradaException.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        ContaResponse response = modelMapper.map(conta, ContaResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("transferir")
    public ResponseEntity<?> transferir(@RequestBody TransferenciaRequest transferenciaRequest) {
        Conta conta = null;

        try {
            conta = contaService.trasnferir(transferenciaRequest.getIdContaRemetente(), transferenciaRequest.getIdContaDestino(), transferenciaRequest.getValor());
        } catch (ContaNaoEncontradaException contaNaoEncontradaException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(contaNaoEncontradaException.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        ContaResponse response = modelMapper.map(conta, ContaResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("alterar-ativo/{id}")
    public ResponseEntity<?> alterarAtivo(@PathVariable("id") Long idConta) {

        Conta conta = null;

        try {
            conta = contaService.alterarAtivo(idConta);
        } catch (ContaNaoEncontradaException contaNaoEncontradaException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(contaNaoEncontradaException.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        ContaResponse response = modelMapper.map(conta, ContaResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("buscar/{id}")
    public ResponseEntity<?> buscarconta(@PathVariable("id") long idConta) {
        Conta conta = null;

        try {
            conta = contaService.buscar(idConta);
        } catch (ContaNaoEncontradaException contaNaoEncontradaException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(contaNaoEncontradaException.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        ContaResponse response = modelMapper.map(conta, ContaResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("buscar")
    public ResponseEntity<?> buscarTodos() {
        List<Conta> contas = null;

        try {
            contas = contaService.buscarTodos();
        } catch (ContaNaoEncontradaException contaNaoEncontradaException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(contaNaoEncontradaException.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }

        List<ContaResponseReduzido> response = new ArrayList<>(contas.size());
        for (Conta conta : contas) {
            response.add(modelMapper.map(conta, ContaResponseReduzido.class));

        }
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}