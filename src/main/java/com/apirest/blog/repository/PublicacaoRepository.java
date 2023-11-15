package com.apirest.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.apirest.blog.entities.Publicacao;

@Repository
public interface PublicacaoRepository extends JpaRepository<Publicacao, Long>{
    
}
