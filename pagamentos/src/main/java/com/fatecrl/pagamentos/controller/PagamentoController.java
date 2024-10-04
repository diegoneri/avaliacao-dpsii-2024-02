package com.fatecrl.pagamentos.controller;

import java.net.URI;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.fatecrl.pagamentos.model.Pagamento;
import com.fatecrl.pagamentos.service.PagamentoService;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {
    
    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public ResponseEntity<List<Pagamento>> getAllPagamentos() {
        return ResponseEntity.ok(pagamentoService.getAllPagamentos());
    }

    @GetMapping("/usuario/{idUsuario}")
    public ResponseEntity<List<Pagamento>> getPagamentoByIdUsuario(@PathVariable Integer idUsuario) {
        List<Pagamento> pagamentos = pagamentoService.getPagamentoByIdUsuario(idUsuario);
        if (pagamentos.size() > 0) {
            return ResponseEntity.ok(pagamentos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(params = {"valorMinimo", "valorMaximo"})
    public ResponseEntity<List<Pagamento>> getPagamentoByNome(@RequestParam Double valorMinimo
                                                      , @RequestParam Double valorMaximo) {
        List<Pagamento> pagamentos = pagamentoService.getPagamentoByValores(valorMinimo, valorMaximo);
        if (pagamentos.size() > 0) {
            return ResponseEntity.ok(pagamentos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pagamento> getPagamentoById(@PathVariable Long id) {
        Pagamento produto = pagamentoService.getPagamentoById(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Pagamento> createPagamento(@RequestBody Pagamento produto) {
        boolean createdPagamento = pagamentoService.createPagamento(produto);
        
        if (createdPagamento) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produto.getId())
                .toUri();
            
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Pagamento> updateStatusPagamento(@PathVariable Long id, @RequestBody Pagamento produto) {
        Pagamento updatedPagamento = pagamentoService.uplocaldateStatusPagamento(id, produto.getStatus());
        if (updatedPagamento != null) {
            return ResponseEntity.ok(updatedPagamento);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePagamento(@PathVariable Long id) {
        boolean deleted = pagamentoService.deletePagamento(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
