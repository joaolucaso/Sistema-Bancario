package com.banco.domain.conta.historico.model;

import com.banco.domain.conta.model.Conta;
import com.banco.application.service.ContaService;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
public class Historico {

    private static final String MENSGEM_CADASTRO = "Cadastrou a conta com R$ %s";
    private static final String MENSGEM_SAQUE = "Sacou R$ %s";
    private static final String MENSGEM_DEPOSITO = "Depositou R$ %s";
    private static final String MENSGEM_TRANSFERENCIA = "Trasferiu R$ %s de %d para %d  ";
    private static final String MENSGEM_ALTERACAO_ATIVO = "Alterou o ativo para %s";


    private Conta remetente;
    private Conta destinatario;
    private TipoOperacao tipoOperacao;
    private String descricao;
    private LocalDateTime dataCadastro;

    private Historico() {
    }

    private Historico(Conta remetente, Conta destinatario, TipoOperacao tipoOperacao, String descricao) {
        this.remetente = remetente;
        this.destinatario = destinatario;
        this.tipoOperacao = tipoOperacao;
        this.descricao = descricao;
        this.dataCadastro = LocalDateTime.now();
    }

    public static Historico cadastro(Conta remetente) {
        Historico historico = new Historico(remetente, null, TipoOperacao.CADASTRO, String.format
                (MENSGEM_CADASTRO, remetente.getSaldo()));
        remetente.addHistorico(historico);
        return historico;
    }

    public static Historico saque(Conta remetente, BigDecimal valor) {
        Historico historico = new Historico(remetente, null, TipoOperacao.SAQUE,
                String.format(MENSGEM_SAQUE, valor));
        remetente.addHistorico(historico);
        return historico;
    }

    public static Historico deposito(Conta remetente, BigDecimal valor) {
        Historico historico = new Historico(remetente, null, TipoOperacao.DEPOSITO,
                String.format(MENSGEM_DEPOSITO, valor));
        remetente.addHistorico(historico);
        return historico;
    }

    public static Historico transferencia(Conta remetente,Conta destinatario,  BigDecimal valor) {
        Historico historicoRemetente = new Historico(remetente, null, TipoOperacao.TRANSFERENCIA,
                String.format("Transferiu R$ %s para %d", valor, destinatario.getNumero()
                )
        );


        Historico historicoDestinatario = new Historico(destinatario, null, TipoOperacao.TRANSFERENCIA,
                String.format("Recebeu R$ %s de %d", valor, remetente.getNumero()));


        remetente.addHistorico(historicoRemetente);
        destinatario.addHistorico(historicoDestinatario);
        return historicoRemetente;
    }


    public static Historico alterarAtivo(Conta remetente) {
        Historico historico = new Historico(remetente, null, TipoOperacao.ALTERAR_ATIVO, String.format
                (MENSGEM_TRANSFERENCIA, remetente.isAtivo()));
        remetente.addHistorico(historico);
        return historico;
    }


    @Override
    public String toString() {
        return "Historico{" +
                "tipoOperacao=" + tipoOperacao +
                ", descricao='" + descricao + '\'' +
                ", dataCadastro=" + dataCadastro +
                '}';
    }

    public enum TipoOperacao {

        CADASTRO("cadastro"),
        SAQUE("saque"),
        DEPOSITO("deposito"),
        TRANSFERENCIA("trasferencia"),
        ALTERAR_ATIVO("Alterar ativo");
        private String label;

        TipoOperacao(String label) {
            this.label = label;
        }

        public String getLabel() {
            return this.label;
        }
    }


}
