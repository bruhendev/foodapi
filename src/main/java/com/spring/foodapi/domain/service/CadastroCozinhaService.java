package com.spring.foodapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.spring.foodapi.domain.exception.EntidadeEmUsoException;
import com.spring.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.spring.foodapi.domain.model.Cozinha;
import com.spring.foodapi.domain.repository.CozinhaRepository;

@Service
public class CadastroCozinhaService {

    @Autowired
    private CozinhaRepository Cozinharepository;

    public Cozinha salvar(Cozinha cozinha) {
        return Cozinharepository.save(cozinha);
    }

    public void excluir(Long id) {
        try {
            Cozinharepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("Entidade não encontrada");
        }
        catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException("Cozinha não pode ser removida");
        }
    }
}
