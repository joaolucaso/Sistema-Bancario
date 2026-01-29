package com.banco;

import com.banco.application.service.ContaService;
import com.banco.domain.conta.cliente.model.Cliente;
import com.banco.domain.conta.model.Conta;
import com.banco.infrastructure.utils.LogBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Scanner;

@SpringBootApplication
public class BancoApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(BancoApplication.class);

    private Scanner scan;
    private ContaService contaService;

//    public BancoApplication(ContaService contaService) {
//        this.contaService = contaService;
//        this.scan = new Scanner(System.in);
//
//    }


    public static void main(String[] args) {

        ApplicationContext app = SpringApplication.run(BancoApplication.class, args);

    }

    public void run() {
        clear();
        try {
            menuPrincipal();
        } catch (Exception exception) {
            LOGGER.error(LogBuilder.of()
                    .header("Um erro aconteceu")
                    .row("erro", exception.getMessage()).build());

        }
    }

    private void menuPrincipal() {
        String opcao = "";
        do {
            clear();
            System.out.println("Menu Principal");
            System.out.println("1 - Cadastar");
            System.out.println("2 - Depositar");
            System.out.println("3 - sacar");
            System.out.println("4 - Transferir");
            System.out.println("5 - Alterar ativo");
            System.out.println("6 - Buscar");
            System.out.println("q - Sair");

            System.out.println("Escolha uma opção:");
            opcao = scan.nextLine();


            try {
                switch (opcao) {
                    case "1":
                        menuCadastrar();
                        break;
                    case "2":
                        menuDepositar();
                        break;
                    case "3":
                        menuSaque();
                        break;
                    case "4":
                        menuTransferir();
                        break;
                    case "5":
                        menuAlterarAtivo();
                        break;
                    case "6":
                        menuBuscar();
                        break;
                    case "q":
                        clear();
                        System.out.println("Saindo...");
                        sair();
                        break;
                    default:
                        System.out.println("Opção Inválida");
                        aguardarEnter();

                }
            } catch (Exception exception) {
                LOGGER.error(LogBuilder.of()
                        .header("Um erro aconteceu")
                        .row("erro", exception.getMessage()).build());

                aguardarEnter();
            }
        } while (!opcao.equals("q"));
    }

    private void menuCadastrar() {
        clear();
        System.out.println("Menu cadastrar");
        System.out.println("Nome: ");
        String nome = scan.nextLine();

        System.out.println("valor inicial: ");
        BigDecimal valor = BigDecimal.valueOf(scan.nextDouble());

        Cliente cliente = new Cliente();
        cliente.setNome(nome);

        Conta conta = new Conta(cliente, valor);
        conta = contaService.cadastrar(conta);
        System.out.println("Conta Cadastrada!");
        System.out.println(String.format("Número: %d, Nome: %s, Saldo: R$ %.2f", conta.getId(),
                conta.getCliente().getNome(), conta.getSaldo()));
        aguardarEnter();

    }

    private void menuDepositar() {
        clear();

        System.out.println("Depositar");
        System.out.println("Número da Conta: ");
        int numero = scan.nextInt();

        System.out.println("Valor: ");

        BigDecimal valor = BigDecimal.valueOf(scan.nextDouble());
        Conta conta = contaService.depositar(numero, valor);

        System.out.println("deposito realizado!");
        System.out.println(String.format("Saldo: R$ %.2f", conta.getSaldo()));

        aguardarEnter();


    }

    private void menuSaque() {
        clear();

        System.out.println("Sacar");
        System.out.println("Número da Conta: ");
        int numero = scan.nextInt();

        System.out.println("Valor: ");
        BigDecimal valor = BigDecimal.valueOf(scan.nextDouble());
        Conta conta = contaService.sacar(numero, valor);
        System.out.println("saque realizado!");
        System.out.println(String.format("Saldo: R$ %.2f", conta.getSaldo()));

        aguardarEnter();

    }

    private void menuTransferir() {
        clear();
        System.out.println("Transferir");

        System.out.println("Numero da Conta  remetente: ");
        int numeroRemetente = scan.nextInt();


        System.out.println("Numero da Conta  do Destinatario: ");
        int numeroDestinatario = scan.nextInt();

        System.out.println("Valor: ");
        BigDecimal valor = BigDecimal.valueOf(scan.nextDouble());

        Conta contaRemetente = contaService.trasnferir(numeroRemetente, numeroDestinatario, valor);

        System.out.println("trasnferir realizado!");
        System.out.println(String.format("Saldo: R$ %.2f", contaRemetente.getSaldo()));

        aguardarEnter();

    }

    private void menuAlterarAtivo() {
        clear();
        System.out.println("Alterar ativo");


        System.out.println("Numero da Conta : ");
        int numero = scan.nextInt();
        Conta conta = contaService.alterarAtivo(numero);

        System.out.println("alteração realizada!");
        System.out.println(String.format("Ativo: R$ %d", conta.isAtivo()));

        aguardarEnter();
    }

    private void menuBuscar() {
        clear();
        System.out.println("Buscar");
        System.out.println("Número da Conta: ");
        int numero = scan.nextInt();
        Conta conta = contaService.buscar(numero);
        System.out.println(conta);

        aguardarEnter();
    }

    private void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else
                new ProcessBuilder("clear").inheritIO().start().waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();

        }

    }

    private void aguardarEnter() {
        System.out.println("Pressione ENTER para continuar!");
        scan = new Scanner(System.in);
        scan.nextLine();


    }

    private void sair() {
        System.exit(0);


    }

}
