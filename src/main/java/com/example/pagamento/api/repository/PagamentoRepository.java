package com.example.pagamento.api.repository;

import com.example.pagamento.api.enums.MetodoPagamento;
import com.example.pagamento.api.model.Pagamento;
import com.example.pagamento.api.enums.StatusPagamentoEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>, JpaSpecificationExecutor<Pagamento> {

}