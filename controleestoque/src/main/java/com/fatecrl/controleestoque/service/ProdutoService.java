package com.fatecrl.controleestoque.service;

import com.fatecrl.controleestoque.model.Produto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProdutoService {
    private final List<Produto> produtosList;

    private Produto produtoFake(){
        Produto produto = new Produto();
        produto.setId(1L);
        produto.setNome("Produto Fake");
        produto.setDescricao("Descrição do Produto Fake");
        produto.setPreco(10.0);
        produto.setQuantidade(10);
        return produto;
    }

    public ProdutoService(){
        produtosList = new ArrayList<>();
        produtosList.add(produtoFake());
    }

    public List<Produto> getAllProdutos() {
        return produtosList;
    }

    public Produto getProduto(Produto _produto) {
        return produtosList.stream()
                .filter(produto -> produto.equals(_produto))
                .findFirst()
                .orElse(null);
    }      
    

    public List<Produto> getProdutoByNome(String nome) {
        return produtosList.stream()
                .filter(produto -> produto.getNome().toLowerCase().contains(nome.toLowerCase()))
                .toList();
    }      

    public Produto getProdutoById(Long id) {
        return getProduto(new Produto(id));
    }

    public boolean createProduto(Produto produto) {
        produto.setId(produtosList.size() + 1L);
        return produtosList.add(produto);
    }

    public Produto updateProduto(Long id, Produto produto) {
        Produto existingProduto = this.getProdutoById(id);
        if (existingProduto != null) {
            if (!produto.getNome().isEmpty()){
                existingProduto.setNome(produto.getNome());
            }

            if (!produto.getDescricao().isEmpty()){
                existingProduto.setDescricao(produto.getDescricao());
            }
            
            if (produto.getPreco() != null && produto.getPreco() > 0){
                existingProduto.setPreco(produto.getPreco());
            }
            
            if (produto.getQuantidade() != null && produto.getQuantidade() > 0){
                existingProduto.setQuantidade(produto.getQuantidade());
            }

            return existingProduto;
        } else {
            return null;
        }
    }

    public boolean deleteProduto(Long id) {
        Produto existingProduto = this.getProdutoById(id);
        if (existingProduto != null) {
            produtosList.remove(existingProduto);
            return true;
        } else {
            return false;
        }
    }
}