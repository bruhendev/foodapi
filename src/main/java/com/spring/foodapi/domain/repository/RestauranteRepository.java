package com.spring.foodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.foodapi.domain.model.Restaurante;

public interface RestauranteRepository extends JpaRepository<Restaurante, Long>{
    
}
