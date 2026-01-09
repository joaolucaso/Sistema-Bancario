package com.banco;

import com.banco.application.service.ContaService;
import com.banco.domain.conta.cliente.model.Cliente;
import com.banco.domain.conta.model.Conta;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;

@SpringBootApplication
public class BancoApplication {

    private ContaService contaService;

    public BancoApplication(ContaService contaService) {
        this.contaService = contaService;
    }


    public static void main(String[] args) {

        ApplicationContext app = SpringApplication.run(BancoApplication.class, args);
        BancoApplication bancoApplication = app.getBean(BancoApplication.class);
        bancoApplication.run();

    }

    public void run() {
        Cliente joao = new Cliente("joao");
        Cliente maria = new Cliente("maria");
        Conta contaJoao = new Conta(joao, BigDecimal.valueOf(1000));
        Conta contamaria = new Conta(maria, BigDecimal.valueOf(1000));

contaService.cadastrar(contaJoao);
contaService.cadastrar(contamaria);
contaService.alterarAtivo(1);
        System.out.println(contaService.buscar(1));
        System.out.println(contaService.buscar(2));
    }
}
