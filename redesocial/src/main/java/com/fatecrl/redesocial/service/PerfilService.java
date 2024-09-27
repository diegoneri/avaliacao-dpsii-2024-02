package com.fatecrl.redesocial.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatecrl.redesocial.model.Perfil;

@Service
public class PerfilService {

    private final List<Perfil> perfilsList;

    public PerfilService(){
        perfilsList = new ArrayList<>();
        perfilsList.add(perfilFake());
    }

    public List<Perfil> getAllPerfils() {
        return perfilsList;
    }

    public Perfil getPerfil(Perfil _perfil) {
        return perfilsList.stream()
                .filter(perfil -> perfil.equals(_perfil))
                .findFirst()
                .orElse(null);
    }

    public List<Perfil> getPerfisByDataCriacao(LocalDate dataCriacao) {
        return perfilsList.stream()
                .filter(perfil -> perfil.getDataCriacao().equals(dataCriacao))
                .toList();
    }      
    

    public Perfil getPerfilById(Long id) {
        return getPerfil(new Perfil(id));
    }      

    public boolean createPerfil(Perfil perfil) {
        perfil.setId(perfilsList.size() + 1L);
        return perfilsList.add(perfil);
    }

    public Perfil updatePerfil(Long id, Perfil perfil) {
        Perfil existingPerfil = this.getPerfilById(id);
        if (existingPerfil != null) {
            if (!perfil.getNome().isEmpty()){
                existingPerfil.setNome(perfil.getNome());
            }

            if (!perfil.getEmail().isEmpty()){
                existingPerfil.setEmail(perfil.getEmail());
            }

            if (!perfil.getBiografia().isEmpty()){
                existingPerfil.setBiografia(perfil.getBiografia());
            }
            
            if (perfil.getDataCriacao() != null){
                existingPerfil.setDataCriacao(perfil.getDataCriacao());
            }

            return existingPerfil;
        } else {
            return null;
        }
    }

    public boolean deletePerfil(Long id) {
        Perfil existingPerfil = this.getPerfilById(id);
        if (existingPerfil != null) {
            perfilsList.remove(existingPerfil);
            return true;
        } else {
            return false;
        }
    }

    private Perfil perfilFake(){
        Perfil perfil = new Perfil();
        perfil.setId(1L);
        perfil.setNome("lala");
        perfil.setEmail("lalal@example.com");
        perfil.setBiografia("I'm a fake profile");
        perfil.setFotoPerfil("c:/foto.png");
        perfil.setDataCriacao(LocalDate.now());
        return perfil;
    }
}
