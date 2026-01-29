package com.banco.domain.conta.controller;


import com.banco.application.service.ContaService;
import com.banco.domain.conta.cliente.model.Cliente;
import com.banco.domain.conta.historico.model.Historico;
import com.banco.domain.conta.model.Conta;
import com.banco.presentation.conta.resquest.ContaRequest;
import com.banco.presentation.conta.resquest.response.ContaResponse;
import com.banco.presentation.historico.response.HistoricoResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/conta/")
public class ContaController {


    private final ContaService contaService;

    public ContaController(ContaService contaService) {
        this.contaService = contaService;
    }

    @PostMapping("cadastrar")
    public ResponseEntity<ContaController> cadastrar(@RequestBody ContaRequest contaRequest) {
        Conta conta = new Conta(new Cliente(0l,contaRequest.getCliente().getNome()),contaRequest.getSaldo());
        conta = contaService.cadastrar(conta);

        List<HistoricoResponse>historicoResponses = new ArrayList<>();

        for (Historico historico : conta.getHistoricos()) {
            historicoResponses.add(
                    new HistoricoResponse(historico.getTipoOperacao().getLabel(),historico.getDescricao(),historico.getDataCadastro()));
        }
        ContaResponse response = new ContaResponse(new ContaResponse(conta.getCliente().getNome()),conta.getSaldo(),historicoResponses) ;
        return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
}
