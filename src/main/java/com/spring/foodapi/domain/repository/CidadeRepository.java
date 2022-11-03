package com.spring.foodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.foodapi.domain.model.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{
    
}
