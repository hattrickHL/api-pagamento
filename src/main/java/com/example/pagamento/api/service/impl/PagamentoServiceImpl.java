package com.example.pagamento.api.service.impl;

import com.example.pagamento.api.dto.PagamentoDTO;
import com.example.pagamento.api.enums.MetodoPagamento;
import com.example.pagamento.api.model.Pagamento;
import com.example.pagamento.api.enums.StatusPagamentoEnum;
import com.example.pagamento.api.repository.PagamentoRepository;

import com.example.pagamento.api.service.PagamentoService;
import com.example.pagamento.api.specification.PagamentoSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PagamentoServiceImpl implements PagamentoService {
    @Autowired
    private PagamentoRepository pagamentoRepository;

    public Optional<Pagamento> buscarPagamentoPorId(Long id) {
        return pagamentoRepository.findById(id);
    }

    public List<Pagamento> buscarPagamentosFiltro(Integer codigoDebito, String cpfCnpj, StatusPagamentoEnum status) {
        PagamentoSpecification specification = new PagamentoSpecification(codigoDebito, cpfCnpj, status);
        return pagamentoRepository.findAll(specification);
    }


    public PagamentoDTO criarPagamento(PagamentoDTO pagamentoDTO) {
        Pagamento pagamento = new Pagamento(pagamentoDTO);

        if (verificaSePagamentoNaoCreditoOuDebito(pagamento)){pagamento.setNumeroCartao(null);}
        else if (verificaSeNumeroCartaoNulo(pagamento)) {
            throw new IllegalArgumentException("O numero do cartão é obrigatorio para paramentos com cartão de credito ou debito");
        }

        pagamento.setStatus(StatusPagamentoEnum.PENDENTE_PROCESSAMENTO);
        return new PagamentoDTO(pagamentoRepository.save(pagamento));
    }
    public PagamentoDTO atualizarStatusPagamento (Long id, PagamentoDTO pagamentoDTO) {
        Pagamento pagamentoExistente = buscarPagamentoPorId(id).orElse(null);

        if (pagamentoExistente != null) {
            StatusPagamentoEnum statusAtual = pagamentoExistente.getStatus();
            StatusPagamentoEnum novoStatus = pagamentoDTO.getStatus();

            switch (statusAtual) {
                case PENDENTE_PROCESSAMENTO: // status pode mudar para outros status
                    if (novoStatus == StatusPagamentoEnum.PROCESSADO_COM_SUCESSO || novoStatus == StatusPagamentoEnum.PROCESSADO_COM_FALHA) {
                        pagamentoExistente.setStatus(novoStatus);
                    } else {
                        throw new IllegalArgumentException("O Status não pode ser alterado");
                    }
                    break;
                case PROCESSADO_COM_FALHA: // status pode mudar para PENDENTE_PROCESSAMENTO
                    if (novoStatus == StatusPagamentoEnum.PENDENTE_PROCESSAMENTO) {
                        pagamentoExistente.setStatus(novoStatus);
                    } else {
                        throw new IllegalArgumentException("O Status não pode ser alterado");
                    }
                    break;
                case PROCESSADO_COM_SUCESSO: // status não pode ser alterado
                    throw new IllegalArgumentException("O Status não pode ser alterado");
                default:
                    throw new IllegalArgumentException("O Status não pode ser alterado");
            }
            return new PagamentoDTO(pagamentoRepository.save(pagamentoExistente));
        } else {
            throw new IllegalArgumentException("O Pagamento não existe");
        }
    }

    public void deletarPagamento(Long id) {
        Pagamento pagamentoExistente = buscarPagamentoPorId(id).orElse(null);

        if (pagamentoExistente != null && pagamentoExistente.getStatus() == StatusPagamentoEnum.PENDENTE_PROCESSAMENTO) {
            pagamentoExistente.setStatus(StatusPagamentoEnum.INATIVO);
            pagamentoRepository.save(pagamentoExistente);
        }else {
            throw new IllegalArgumentException("O pagamento não pode ser excluido");
        }
    }

    public Boolean verificaSePagamentoNaoCreditoOuDebito(Pagamento pagamento) {
      return pagamento.getMetodoPagamento() == MetodoPagamento.BOLETO || pagamento.getMetodoPagamento() == MetodoPagamento.PIX;
    }

    public Boolean verificaSeNumeroCartaoNulo(Pagamento pagamento) {
        return pagamento.getNumeroCartao() == null;
    }
}