package com.fatecrl.blog.controller;

import com.fatecrl.blog.model.Postagem;
import com.fatecrl.blog.service.PostagemService;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/postagens")
public class PostagemController {
    
    @Autowired
    private PostagemService postagemService;

    @GetMapping(produces = "application/json")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Retorna a lista de postagens")
    })
    @Operation(summary = "Retorna a lista de postagens")
    public ResponseEntity<List<Postagem>> getAllPostagems() {
        return ResponseEntity.ok(postagemService.getAllPostagems());
    }

    @GetMapping(params = "autor")
    @Operation(summary = "Retorna a lista de postagens por nome do autor")
    public ResponseEntity<List<Postagem>> getPostagemByAutor(@RequestParam(required = false) String autor) {
        List<Postagem> postagens = postagemService.getPostagensByAutor(autor);
        if (postagens.size() > 0) {
            return ResponseEntity.ok(postagens);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retorna a lista de postagens por id")
    public ResponseEntity<Postagem> getPostagemById(@PathVariable Long id) {
        Postagem postagem = postagemService.getPostagemById(id);
        if (postagem != null) {
            return ResponseEntity.ok(postagem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Postagem> createPostagem(@RequestBody Postagem postagem) {
        boolean createdPostagem = postagemService.createPostagem(postagem);
        
        if (createdPostagem) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postagem.getId())
                .toUri();
            
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Postagem> updatePostagem(@RequestBody Postagem postagem) {
        Postagem updatedPostagem = postagemService.updatePostagem(postagem.getId(), postagem);
        if (updatedPostagem != null) {
            return ResponseEntity.ok(updatedPostagem);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @Hidden
    public ResponseEntity<Void> deletePostagem(@PathVariable Long id) {
        boolean deleted = postagemService.deletePostagem(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}