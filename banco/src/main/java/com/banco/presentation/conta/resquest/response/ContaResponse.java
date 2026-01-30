package com.banco.presentation.conta.resquest.response;

import com.banco.domain.conta.historico.model.Historico;
import com.banco.presentation.cliente.request.ClienteRequest;
import com.banco.presentation.cliente.response.ClienteResponse;
import com.banco.presentation.historico.response.HistoricoResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaResponse {
private Long id;
    private ClienteResponse cliente;
    private BigDecimal saldo;
    private List<HistoricoResponse> historicos;
}
