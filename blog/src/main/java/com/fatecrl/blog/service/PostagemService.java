package com.fatecrl.blog.service;

import java.util.ArrayList;
import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fatecrl.blog.model.Postagem;

@Service
public class PostagemService {

    private final List<Postagem> postagemsList;

    public PostagemService(){
        postagemsList = new ArrayList<>();
        postagemsList.add(postagemFake());
    }

    public List<Postagem> getAllPostagems() {
        return postagemsList;
    }

    public Postagem getPostagem(Postagem _postagem) {
        return postagemsList.stream()
                .filter(postagem -> postagem.equals(_postagem))
                .findFirst()
                .orElse(null);
    }

    public List<Postagem> getPostagensByAutor(String autor) {
        return postagemsList.stream()
                .filter(postagem -> postagem.getAutor().toLowerCase().contains(autor.toLowerCase()))
                .toList();
    }      
    

    public Postagem getPostagemById(Long id) {
        return getPostagem(new Postagem(id));
    }      

    public boolean createPostagem(Postagem postagem) {
        postagem.setId(postagemsList.size() + 1L);
        return postagemsList.add(postagem);
    }

    public Postagem updatePostagem(Long id, Postagem postagem) {
        Postagem existingPostagem = this.getPostagemById(id);
        if (existingPostagem != null) {
            if (!postagem.getTitulo().isEmpty()){
                existingPostagem.setTitulo(postagem.getTitulo());
            }

            if (!postagem.getConteudo().isEmpty()){
                existingPostagem.setConteudo(postagem.getConteudo());
            }

            if (!postagem.getAutor().isEmpty()){
                existingPostagem.setAutor(postagem.getAutor());
            }
            
            if (postagem.getDataPublicacao() != null){
                existingPostagem.setDataPublicacao(postagem.getDataPublicacao());
            }

            return existingPostagem;
        } else {
            return null;
        }
    }

    public boolean deletePostagem(Long id) {
        Postagem existingPostagem = this.getPostagemById(id);
        if (existingPostagem != null) {
            postagemsList.remove(existingPostagem);
            return true;
        } else {
            return false;
        }
    }

    private Postagem postagemFake(){
        Postagem postagem = new Postagem();
        postagem.setId(1L);
        postagem.setTitulo("Postagem Fake");
        postagem.setConteudo("Conteudo Fake");
        postagem.setAutor("Autor Fake");
        postagem.setDataPublicacao(LocalDate.now());
        return postagem;
    }
}
