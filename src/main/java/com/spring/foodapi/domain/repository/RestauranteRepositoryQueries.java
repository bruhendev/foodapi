package com.spring.foodapi.domain.repository;

import java.math.BigDecimal;
import java.util.List;

import com.spring.foodapi.domain.model.Restaurante;

public interface RestauranteRepositoryQueries {
    
    List<Restaurante> find(String nome, BigDecimal taxaFreteInicial, BigDecimal taxaFreteFinal);

    List<Restaurante> findComFreteGratis(String nome);
}
