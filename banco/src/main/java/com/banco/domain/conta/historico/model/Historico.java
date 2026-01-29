package com.banco.domain.conta.historico.model;

import com.banco.domain.conta.model.Conta;
import com.banco.application.service.ContaService;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "t_historico")
public class Historico {

    private static final String MENSGEM_CADASTRO = "Cadastrou a conta com R$ %s";
    private static final String MENSGEM_SAQUE = "Sacou R$ %s";
    private static final String MENSGEM_DEPOSITO = "Depositou R$ %s";
    private static final String MENSGEM_TRANSFERENCIA = "Trasferiu R$ %s de %d para %d  ";
    private static final String MENSGEM_ALTERACAO_ATIVO = "Alterou o ativo para %s";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name ="id_remetente")
    private Conta remetente;

    @ManyToOne
    @JoinColumn(name ="id_destinatario")
    private Conta destinatario;

@Enumerated(EnumType.STRING)
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
        Historico historico = new Historico(remetente, destinatario, TipoOperacao.TRANSFERENCIA,
                String.format(MENSGEM_TRANSFERENCIA, valor, remetente.getId(), destinatario.getId()));
        remetente.addHistorico(historico);
       destinatario.addHistorico(historico);
        return historico;
    }


    public static Historico alterarAtivo(Conta remetente) {
        Historico historico = new Historico(remetente, null, TipoOperacao.ALTERAR_ATIVO, String.format
                (MENSGEM_ALTERACAO_ATIVO, remetente.isAtivo()));
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
