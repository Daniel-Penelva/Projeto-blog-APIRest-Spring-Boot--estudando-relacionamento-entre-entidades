package com.apirest.blog.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.blog.entities.Comentario;

@Repository
public interface ComentarioRepository extends JpaRepository<Comentario, Long> {

    Page<Comentario> findByPublicacaoId(Long publicacaoId, Pageable pageable);

    Optional<Comentario> findByIdAndPublicacaoId(Long comentarioId, Long publicacaoId);

}
