package com.example.pagamento.api.specification;

import com.example.pagamento.api.enums.StatusPagamentoEnum;
import com.example.pagamento.api.model.Pagamento;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.logging.log4j.util.Strings;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class PagamentoSpecification implements Specification<Pagamento> {

    public Integer codigoDebito;
    public String cpfCnpj;
    public StatusPagamentoEnum status;

    public PagamentoSpecification(Integer codigoDebito, String cpfCnpj, StatusPagamentoEnum status) {
        this.codigoDebito = codigoDebito;
        this.cpfCnpj = cpfCnpj;
        this.status = status;
    }

    @Override
    public Predicate toPredicate(Root<Pagamento> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();
        if (Strings.isNotEmpty(this.cpfCnpj)) {
            predicates.add(criteriaBuilder.equal(root.get("cpfCnpj"), this.cpfCnpj));
        }
        if (this.codigoDebito != null) {
            predicates.add(criteriaBuilder.equal(root.get("codigoDebito"), this.codigoDebito));
        }
        if (this.status != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), this.status.toString()));
        }
        return criteriaBuilder.and(predicates.stream().toArray(Predicate[]::new));
    }
}
