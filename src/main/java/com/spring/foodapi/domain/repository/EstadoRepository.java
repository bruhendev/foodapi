package com.spring.foodapi.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.foodapi.domain.model.Estado;

public interface EstadoRepository extends JpaRepository<Estado, Long>{
    
}
