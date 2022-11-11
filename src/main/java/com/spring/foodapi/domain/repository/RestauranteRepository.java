package com.spring.foodapi.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.spring.foodapi.domain.model.Restaurante;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante, Long>, RestauranteRepositoryQueries, JpaSpecificationExecutor<Restaurante>{
    
    List<Restaurante> taxaFreteBetween(BigDecimal taxaInicial, BigDecimal taxaFinal);

    // @Query("from Restaurante where nome like %:nome% and cozinha.id = :cozinha")
    List<Restaurante> consultarPorNome(@Param("nome") String nome, @Param("id") Long cozinha);

    
}
