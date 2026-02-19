package com.banco.application.autenticacao.controller;

import com.banco.application.autenticacao.service.AutenticacaoService;
import com.banco.application.service.ContaService;
import com.banco.domain.conta.model.Conta;
import com.banco.presentation.auth.request.LoginRequest;
import com.banco.presentation.auth.response.LoginResponse;
import com.banco.presentation.conta.resquest.ContaRequest;
import com.banco.presentation.conta.resquest.response.ContaResponse;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/autenticacao/")
public class AutenticacaoController {

    private final AutenticacaoService autenticacaoService;


    public AutenticacaoController(AutenticacaoService autenticacaoService) {
        this.autenticacaoService = autenticacaoService;

    }

    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {

        LoginResponse response = autenticacaoService.login(loginRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }


}
