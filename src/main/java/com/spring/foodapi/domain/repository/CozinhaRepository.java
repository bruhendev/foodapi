package com.spring.foodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.spring.foodapi.domain.model.Cozinha;

@Repository
public interface CozinhaRepository extends JpaRepository<Cozinha, Long> {

    // List<Cozinha> listar();
    // Cozinha buscar(Long id);
    // Cozinha salvar(Cozinha cozinha);
    // void remover(Cozinha cozinha);
}
