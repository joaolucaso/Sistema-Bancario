package com.banco.presentation.conta.resquest.response;

import com.banco.presentation.cliente.response.ClienteResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ContaResponseReduzido {

    private Long id;
    private ClienteResponse cliente;
    private BigDecimal saldo;
    private boolean ativo;
}
