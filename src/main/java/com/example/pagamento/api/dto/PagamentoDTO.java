package com.example.pagamento.api.dto;

import com.example.pagamento.api.enums.MetodoPagamento;
import com.example.pagamento.api.model.Pagamento;
import com.example.pagamento.api.enums.StatusPagamentoEnum;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class PagamentoDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer codigoDebito;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String cpfCnpj;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private MetodoPagamento metodoPagamento;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String numeroCartao;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal valor;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private StatusPagamentoEnum status;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String error;

    public PagamentoDTO() {
    }
    public PagamentoDTO(String error){
        this.error = error;
    }

    public PagamentoDTO(Pagamento pagamento) {
        this.id = pagamento.getId();
        this.cpfCnpj = pagamento.getCpfCnpj();
        this.metodoPagamento = pagamento.getMetodoPagamento();
        this.numeroCartao = pagamento.getNumeroCartao();
        this.valor = pagamento.getValor();
        this.status = pagamento.getStatus();
        this.codigoDebito = pagamento.getCodigoDebito();

    }
    public PagamentoDTO(long id, String cpfCnpj, MetodoPagamento metodoPagamento, String numeroCartao, BigDecimal valor, StatusPagamentoEnum status){
        this.id = id;
        this.cpfCnpj = cpfCnpj;
        this.metodoPagamento = metodoPagamento;
        this.numeroCartao = numeroCartao;
        this.valor = valor;
        this.status = status;
    }

}
