package com.example.pagamento.api.controller;

import com.example.pagamento.api.dto.PagamentoDTO;
import com.example.pagamento.api.enums.StatusPagamentoEnum;
import com.example.pagamento.api.model.Pagamento;
import com.example.pagamento.api.repository.PagamentoRepository;
import com.example.pagamento.api.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/api/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;
    @Autowired
    private PagamentoRepository pagamentoRepository;

    @PostMapping
    public ResponseEntity<PagamentoDTO> criarPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        PagamentoDTO novoPagamento = pagamentoService.criarPagamento(pagamentoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(novoPagamento);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> atualizarPagamento(@PathVariable Long id, @RequestBody PagamentoDTO pagamentoDTO) {

        PagamentoDTO pagamentoAtualizado = pagamentoService.atualizarStatusPagamento(id, pagamentoDTO);
        return ResponseEntity.ok(pagamentoAtualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPagamento(@PathVariable Long id) {

            pagamentoService.deletarPagamento(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping()
    public ResponseEntity<List<Pagamento>> buscarPagamentosPorFiltro(@RequestParam(required = false) Integer codigoDebito,
                                                                     @RequestParam(required = false) String cpfCnpj,
                                                                     @RequestParam(required = false) StatusPagamentoEnum status) {
        List<Pagamento> pagamentos = pagamentoService.buscarPagamentosFiltro(codigoDebito, cpfCnpj, status);
        return ResponseEntity.ok(pagamentos);
    }

}