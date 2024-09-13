package com.example.pagamento.api.model;

import com.example.pagamento.api.dto.PagamentoDTO;
import com.example.pagamento.api.enums.MetodoPagamento;
import com.example.pagamento.api.enums.StatusPagamentoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;

import jakarta.validation.constraints.Pattern;
import lombok.Data;


import java.math.BigDecimal;

@Data
@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "codigoDebito")
    private Integer codigoDebito;

    @NotNull
    @Column(name = "cpfCnpj")
    @Pattern(regexp = "([0-9]{2}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[\\/]?[0-9]{4}[-]?[0-9]{2})|([0-9]{3}[\\.]?[0-9]{3}[\\.]?[0-9]{3}[-]?[0-9]{2})", message = "CPF ou CNPJ Invalido")
    private String cpfCnpj;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "metodoPagamento")
    private MetodoPagamento metodoPagamento;

    @Column(name = "numeroCartao")
    private String numeroCartao;

    @NotNull
    @DecimalMin(value = "0.01")
    @Column(name = "valor")
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "varchar (30) default 'PENDENTE_PROCESSAMENTO'")
    private StatusPagamentoEnum status;

    public Pagamento() {

    }

    public Pagamento (PagamentoDTO pagamento){
        this.cpfCnpj = pagamento.getCpfCnpj();
        this.metodoPagamento = pagamento.getMetodoPagamento();
        this.numeroCartao = pagamento.getNumeroCartao();
        this.valor = pagamento.getValor();
        this.status = pagamento.getStatus();
        this.codigoDebito = pagamento.getCodigoDebito();
    }

    public Pagamento (String cpfCnpj, MetodoPagamento metodoPagamento, String numeroCartao, BigDecimal valor, StatusPagamentoEnum status){
        this.cpfCnpj = cpfCnpj;
        this.metodoPagamento = metodoPagamento;
        this.numeroCartao = numeroCartao;
        this.valor = valor;
        this.status = status;
    }

}
