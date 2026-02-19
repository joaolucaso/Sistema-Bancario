package com.banco.application.autenticacao.service;

import com.banco.domain.conta.cliente.model.Cliente;
import com.banco.infrastructure.security.service.JwtService;
import com.banco.infrastructure.security.service.SecurityService;
import com.banco.infrastructure.utils.repository.cliente.ClienteRepository;
import com.banco.presentation.auth.request.LoginRequest;
import com.banco.presentation.auth.response.LoginResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class AutenticacaoService {

    private final AuthenticationManager authenticationManager;
    private final ClienteRepository clienteRepository;
    private final JwtService jwtService;
    private final SecurityService securityService;

    public AutenticacaoService(AuthenticationManager authenticationManager,
                               ClienteRepository clienteRepository,
                               SecurityService securityService,
                               JwtService jwtService) {
        this.authenticationManager = authenticationManager;
        this.securityService = securityService;
        this.jwtService = jwtService;
        this.clienteRepository = clienteRepository;
    }

    public LoginResponse login(LoginRequest request) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getNome(),
                        request.getSenha()
                )
        );

        Cliente cliente = clienteRepository.buscarPorNome(request.getNome())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        UserDetails userDetails = securityService.loadUserByUsername(cliente.getNome());

        String token = jwtService.generatetoken(userDetails);

        return new LoginResponse(token);
    }
}
