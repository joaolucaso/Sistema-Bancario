package com.banco.domain.conta.controller;

import com.banco.infrastructure.utils.LogBuilder;
import com.banco.application.service.ContaService;
import com.banco.domain.conta.exeption.ContaNaoEncontradaException;
import com.banco.domain.conta.model.Conta;
import com.banco.presentation.conta.resquest.ContaRequest;
import com.banco.presentation.conta.resquest.response.ContaResponse;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


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
}