package com.fatecrl.blog.controller;

import com.fatecrl.blog.model.Postagem;
import com.fatecrl.blog.service.PostagemService;

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

    @GetMapping
    public ResponseEntity<List<Postagem>> getAllPostagems() {
        return ResponseEntity.ok(postagemService.getAllPostagems());
    }

    @GetMapping(params = "autor")
    public ResponseEntity<List<Postagem>> getPostagemByAutor(@RequestParam String autor) {
        List<Postagem> postagens = postagemService.getPostagensByAutor(autor);
        if (postagens.size() > 0) {
            return ResponseEntity.ok(postagens);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
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
    public ResponseEntity<Void> deletePostagem(@PathVariable Long id) {
        boolean deleted = postagemService.deletePostagem(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}