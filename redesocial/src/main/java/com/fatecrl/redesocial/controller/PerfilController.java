package com.fatecrl.redesocial.controller;

import com.fatecrl.redesocial.model.Perfil;
import com.fatecrl.redesocial.service.PerfilService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/perfis")
public class PerfilController {
    
    @Autowired
    private PerfilService perfilService;

    @GetMapping
    public ResponseEntity<List<Perfil>> getAllPerfils() {
        return ResponseEntity.ok(perfilService.getAllPerfils());
    }

    @GetMapping(params = "dataCriacao")
    public ResponseEntity<List<Perfil>> getPerfilByDataCriacao(@RequestParam
                                                               @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dataCriacao) {
        List<Perfil> perfis = perfilService.getPerfisByDataCriacao(dataCriacao);
        if (perfis.size() > 0) {
            return ResponseEntity.ok(perfis);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Perfil> getPerfilById(@PathVariable Long id) {
        Perfil perfil = perfilService.getPerfilById(id);
        if (perfil != null) {
            return ResponseEntity.ok(perfil);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Perfil> createPerfil(@RequestBody Perfil perfil) {
        boolean createdPerfil = perfilService.createPerfil(perfil);
        
        if (createdPerfil) {
            URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(perfil.getId())
                .toUri();
            
            return ResponseEntity.created(location).build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Perfil> updatePerfil(@RequestBody Perfil perfil) {
        Perfil updatedPerfil = perfilService.updatePerfil(perfil.getId(), perfil);
        if (updatedPerfil != null) {
            return ResponseEntity.ok(updatedPerfil);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePerfil(@PathVariable Long id) {
        boolean deleted = perfilService.deletePerfil(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}