package com.example.pagamento.api.service;

import com.example.pagamento.api.dto.PagamentoDTO;
import com.example.pagamento.api.enums.StatusPagamentoEnum;
import com.example.pagamento.api.model.Pagamento;

import java.util.List;

public interface PagamentoService {

    public void deletarPagamento(Long id);

    public PagamentoDTO criarPagamento(PagamentoDTO pagamentoDTO);

    public PagamentoDTO atualizarStatusPagamento (Long id, PagamentoDTO pagamentoDTO);

    public List<Pagamento> buscarPagamentosFiltro(Integer codigoDebito, String cpfCnpj, StatusPagamentoEnum status);
}
