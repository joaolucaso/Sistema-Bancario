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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
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
    public ResponseEntity<?> depositar(@RequestBody DepositoRequest depositoRequest,Principal principal) {
        Conta conta = null;

        try {
            conta = contaService.depositar(principal.getName(), depositoRequest.getValor());
        } catch (ContaNaoEncontradaException contaNaoEncontradaException) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(contaNaoEncontradaException.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
        }
        ContaResponse response = modelMapper.map(conta, ContaResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("sacar")
    public ResponseEntity<?> sacar(@RequestBody SaqueRequest saqueRequest,Principal principal) {
        Conta conta = null;

        try {
            conta = contaService.depositar(principal.getName(),
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

    @GetMapping("buscar")
    public ResponseEntity<?> buscarconta(Principal principal) {
        Conta conta = null;

        try {
            conta = contaService.buscar(principal.getName());
        } catch (ContaNaoEncontradaException contaNaoEncontradaException) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(contaNaoEncontradaException.getMessage());
        } catch (Exception exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
        ContaResponse response = modelMapper.map(conta, ContaResponse.class);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @GetMapping("buscar-todos")
    public ResponseEntity<Page<?>> buscarTodos(Pageable pageable) {
        Page<Conta> contas = null;
        Page<?> response;
        try {
            contas = contaService.buscarTodos(pageable);
        } catch (ContaNaoEncontradaException contaNaoEncontradaException) {
            response = new PageImpl<>(Arrays.asList(contaNaoEncontradaException.getMessage()), pageable, 1);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception exception) {
            response = new PageImpl<>(Arrays.asList(exception.getMessage()), pageable, 1);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }

        List<ContaResponseReduzido> contaResponseReduzidos = new ArrayList<>(contas.getSize());
        for (Conta conta : contas) {
            contaResponseReduzidos.add(modelMapper.map(conta, ContaResponseReduzido.class));

        }
        response = new PageImpl<>(contaResponseReduzidos, pageable, contaResponseReduzidos.size());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}