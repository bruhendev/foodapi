package com.spring.foodapi.domain.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.spring.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.spring.foodapi.domain.model.Estado;
import com.spring.foodapi.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {

    @Autowired
    EstadoRepository estadoRepository;

    public List<Estado> listar() {
        return estadoRepository.findAll();
    }

    public Estado buscarPorId(Long estadoId) {
        try {
            return estadoRepository.findById(estadoId).get();
        } catch (NoSuchElementException e) {
            throw new EntidadeNaoEncontradaException("Entidade estado com id " + estadoId + " não foi encotrado");
        }
    }

    public Estado salvar(Estado estado) {
        estado.setId(null);
        estado = estadoRepository.save(estado);
        return estado;
    }

    public Estado atualizar(Estado estado, Long estadoId) {
        Estado estadoAtual = estadoRepository.findById(estadoId).get();

        if (estado.getNome() != null && !estado.getNome().trim().isEmpty()) {
            estadoAtual.setNome(estado.getNome());
        }

        return estadoRepository.save(estadoAtual);
    }

    public void deletar(Long estadoId) {
        try {
            estadoRepository.deleteById(estadoId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("");
        }
    }
}
