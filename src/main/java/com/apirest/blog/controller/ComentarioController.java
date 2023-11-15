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

import com.apirest.blog.entities.Comentario;
import com.apirest.blog.exception.ResourceNotFoundException;
import com.apirest.blog.repository.ComentarioRepository;
import com.apirest.blog.repository.PublicacaoRepository;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/comentario")
@AllArgsConstructor
public class ComentarioController {

    public ComentarioRepository comentarioRepository;

    private PublicacaoRepository publicacaoRepository;

    // localhost:8080/api/comentario/search/{publicacaoId}/comentarios
    @GetMapping("/search/{publicacaoId}/comentarios")
    public Page<Comentario> listarComentariosPorPublicacao(@PathVariable(value = "publicacaoId") Long publicacaoId,
            Pageable pageable) {
        return comentarioRepository.findByPublicacaoId(publicacaoId, pageable);
    }

    // localhost:8080/api/comentario/{publicacaoId}/publicacao
    @PostMapping("/{publicacaoId}/publicacao")
    public Comentario criarComentario(@PathVariable(value = "publicacaoId") Long publicacaoId,
            @Valid @RequestBody Comentario comentario) {
        
        return publicacaoRepository.findById(publicacaoId).map(publicacao -> {
            comentario.setPublicacao(publicacao);
            return comentarioRepository.save(comentario);

        }).orElseThrow(() -> new ResourceNotFoundException("Id " + publicacaoId + " de publicacao não encontrado!"));
    }

    // localhost:8080/api/comentario/replace/{comentarioId}/publicacao/{publicacaoId}
    @PutMapping("/replace/{comentarioId}/publicacao/{publicacaoId}")
    public Comentario atualizarComentario(@PathVariable(value = "comentarioId") Long comentarioId,
            @PathVariable(value = "publicacaoId") Long publicacaoId, @Valid @RequestBody Comentario comentario) {

        if (!publicacaoRepository.existsById(publicacaoId)) {
            throw new ResourceNotFoundException("Id " + publicacaoId + " de publicacao não encontrado!");
        }

        return comentarioRepository.findById(comentarioId).map(c -> {
            c.setTexto(comentario.getTexto());
            return comentarioRepository.save(c);

        }).orElseThrow(() -> new ResourceNotFoundException("Id " + comentarioId + " de comentário não encontrado!"));
    }

    // localhost:8080/api/comentario/{comentarioId}/delete//publicacao/{publicacaoId}
    @DeleteMapping("/{comentarioId}/delete//publicacao/{publicacaoId}")
    public ResponseEntity<?> deletarComentario(@PathVariable(value = "comentarioId") Long comentarioId, @PathVariable(value = "publicacaoId") Long publicacaoId) {
        return comentarioRepository.findByIdAndPublicacaoId(comentarioId, publicacaoId).map(c -> {

            comentarioRepository.delete(c);
            return ResponseEntity.ok().build();

        }).orElseThrow(() -> new ResourceNotFoundException("Id " + comentarioId + " de comentário não encontrado e Id " + publicacaoId + " de publicação não encontrado e Id"));
    }

}
