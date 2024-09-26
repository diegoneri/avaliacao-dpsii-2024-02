package com.fatecrl.controleestoque.controller;

import com.fatecrl.controleestoque.model.Produto;
import com.fatecrl.controleestoque.service.ProdutoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoService produtoService;

    @GetMapping
    public ResponseEntity<List<Produto>> getAllProdutos() {
        return ResponseEntity.ok(produtoService.getAllProdutos());
    }

    @GetMapping(params = "nome")
    public ResponseEntity<Produto> getProdutoByNome(@RequestParam String nome) {
        Produto produto = produtoService.getProdutoByNome(nome);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Produto> getProdutoById(@PathVariable Long id) {
        Produto produto = produtoService.getProdutoById(id);
        if (produto != null) {
            return ResponseEntity.ok(produto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Produto> createProduto(@RequestBody Produto produto) {
        boolean createdProduto = produtoService.createProduto(produto);
        
        if (createdProduto) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(produto.getId())
                .toUri();
            
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Produto> updateProduto(@RequestBody Produto produto) {
        Produto updatedProduto = produtoService.updateProduto(produto.getId(), produto);
        if (updatedProduto != null) {
            return ResponseEntity.ok(updatedProduto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduto(@PathVariable Long id) {
        boolean deleted = produtoService.deleteProduto(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}