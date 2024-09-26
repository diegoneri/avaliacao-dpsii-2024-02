package com.fatecrl.pagamentos.service;

import com.fatecrl.pagamentos.model.Pagamento;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PagamentoService {
    private final List<Pagamento> produtosList;

    private Pagamento pagamentoFake(){
        Pagamento pagamento = new Pagamento();
        pagamento.setId(1L);
        pagamento.setValor(100.0);
        pagamento.setFormaPagamento("Cartão de Crédito");
        pagamento.setStatus("Aguardando Pagamento");
        pagamento.setIdUsuario(1);
        pagamento.setDataPagamento(new Date());
        return pagamento;
    }

    public PagamentoService(){
        produtosList = new ArrayList<>();
        produtosList.add(pagamentoFake());
    }

    public List<Pagamento> getAllPagamentos() {
        return produtosList;
    }

    public Pagamento getPagamento(Pagamento _produto) {
        return produtosList.stream()
                .filter(produto -> produto.equals(_produto))
                .findFirst()
                .orElse(null);
    }      

    public List<Pagamento> getPagamentoByIdUsuario(Integer idUsuario) {
        return produtosList.stream()
                .filter(produto -> produto.getIdUsuario().equals(idUsuario))
                .toList();
    }    
    

    public List<Pagamento> getPagamentoByValores(Double valorMinimo, Double valorMaximo) {
        return produtosList.stream()
                .filter(produto -> produto.getValor() >= valorMinimo && produto.getValor() <= valorMaximo)
                .toList();
    }         

    public Pagamento getPagamentoById(Long id) {
        return getPagamento(new Pagamento(id));
    }

    public boolean createPagamento(Pagamento produto) {
        produto.setId(produtosList.size() + 1L);
        return produtosList.add(produto);
    }

    public Pagamento updateStatusPagamento(Long id, String status) {
        Pagamento existingPagamento = this.getPagamentoById(id);
        if (existingPagamento != null) {
            if (!status.isEmpty()){
                existingPagamento.setStatus(status);
            }
            return existingPagamento;
        } else {
            return null;
        }
    }

    public boolean deletePagamento(Long id) {
        Pagamento existingPagamento = this.getPagamentoById(id);
        if (existingPagamento != null) {
            produtosList.remove(existingPagamento);
            return true;
        } else {
            return false;
        }
    }
}