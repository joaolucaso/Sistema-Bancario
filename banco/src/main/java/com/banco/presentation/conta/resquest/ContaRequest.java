package com.banco.presentation.conta.resquest;

import com.banco.presentation.cliente.request.ClienteRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor


public class ContaRequest {
    private ClienteRequest cliente;
    private BigDecimal saldo;

}
