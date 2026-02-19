package com.banco.infrastructure.utils.repository.cliente;

import com.banco.domain.conta.cliente.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    @Query("SELECT c FROM Cliente c WHERE c.nome = :nome")
    Optional<Cliente> buscarPorNome(@Param("nome") String nome);


}
