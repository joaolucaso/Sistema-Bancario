package com.banco.infrastructure.security.service;

import com.banco.domain.conta.cliente.model.Cliente;
import com.banco.infrastructure.utils.repository.cliente.ClienteRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class SecurityService implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    public SecurityService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String nome)
            throws UsernameNotFoundException {

        Cliente cliente = clienteRepository.buscarPorNome(nome)
                .orElseThrow(() -> new UsernameNotFoundException(nome));

        return new User(
                cliente.getNome(),
                cliente.getSenha(),
                Collections.emptyList()
        );
    }
}



