package com.spring.foodapi.domain.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.spring.foodapi.domain.exception.EntidadeEmUsoException;
import com.spring.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.spring.foodapi.domain.model.Cozinha;
import com.spring.foodapi.domain.model.Restaurante;
import com.spring.foodapi.domain.repository.CozinhaRepository;
import com.spring.foodapi.domain.repository.RestauranteRepository;

@Service
public class CadastroRestauranteService {

    @Autowired
    RestauranteRepository restauranteRepository;

    @Autowired
    CozinhaRepository cozinhaRepository;

    public List<Restaurante> listar() {
        return restauranteRepository.findAll();
    }

    public void excluir(Long id) {
        try {
            restauranteRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("Entidade não encontrada");
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Entidade não pode ser removida");
        }
    }

    public Restaurante buscar(Long restauranteId) {
        try {
            return restauranteRepository.findById(restauranteId).get();
        } catch (NoSuchElementException e) {
            throw new EntidadeNaoEncontradaException("Entidade não encontrada");
        }
    }

    public Restaurante salvar(Restaurante restaurante) {

        Long cozinhaId = restaurante.getCozinha().getId();
        try {

            Cozinha cozinha = cozinhaRepository.findById(cozinhaId).get();

            restaurante.setCozinha(cozinha);

            return restauranteRepository.save(restaurante);
        } catch (NoSuchElementException e) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de cozinha com código %d", cozinhaId));
        }

    }

}
