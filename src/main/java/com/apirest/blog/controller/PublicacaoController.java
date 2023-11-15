package com.apirest.blog.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apirest.blog.entities.Publicacao;
import com.apirest.blog.exception.ResourceNotFoundException;
import com.apirest.blog.repository.PublicacaoRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/publicacao")
@AllArgsConstructor
public class PublicacaoController {

    private PublicacaoRepository publicacaoRepository;

    // localhost:8080/api/publicacao/all
    @GetMapping("/all")
    public Page<Publicacao> listarPublicacao(Pageable pageable) {
        return publicacaoRepository.findAll(pageable);
    }

    // localhost:8080/api/publicacao/create
    @PostMapping("/create")
    public Publicacao criarPublicacao(@Valid @RequestBody Publicacao publicacao) {
        return publicacaoRepository.save(publicacao);
    }

    // localhost:8080/api/publicacao/replace/{id}
    @PutMapping("/replace/{id}")
    public Publicacao atualizarPublicacao(@PathVariable Long id, @Valid @RequestBody Publicacao publicacao) {
        return publicacaoRepository.findById(id).map(p -> {
            p.setTitulo(publicacao.getTitulo());
            p.setDescricao(publicacao.getDescricao());
            p.setConteudo(publicacao.getConteudo());

            return publicacaoRepository.save(p);
        }).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " de publicacao não encontrado!"));
    }

    // localhost:8080/api/publicacao/delete/{id}
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deletarPublicacao(@PathVariable Long id) {
        return publicacaoRepository.findById(id).map(p -> {
            publicacaoRepository.delete(p);
            return ResponseEntity.ok().build();
        }).orElseThrow(() -> new ResourceNotFoundException("Id " + id + " de publicacao não encontrado!"));
    }

}
