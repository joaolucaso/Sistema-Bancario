package com.banco.presentation.historico.response;

import com.banco.domain.conta.historico.model.Historico;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HistoricoResponse {
    private String tipoOperacao;

    private String descricao;

    private LocalDateTime dataCadastro;

}
