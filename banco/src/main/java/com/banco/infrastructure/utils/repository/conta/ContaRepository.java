package com.banco.infrastructure.utils.repository.conta;

import com.banco.domain.conta.model.Conta;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContaRepository extends JpaRepository<Conta, Long> {

    @Query("SELECT conta FROM Conta conta WHERE id = :idConta")
    public Optional <Conta> buscarPorId(@Param("idConta") Long idConta);

    @Query("SELECT conta FROM Conta conta WHERE ativo = true")
    public List<Conta> buscarTodosAtivos();

    @Query("SELECT conta FROM Conta conta WHERE ativo = true")
    public Page<Conta> buscarTodosAtivos(Pageable pageable);

    @Query("SELECT conta FROM Conta conta WHERE ativo = true")
    public Page<Conta> buscarTodos(Pageable pageable);

}
