package com.spring.foodapi.domain.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.spring.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.spring.foodapi.domain.model.Cidade;
import com.spring.foodapi.domain.model.Estado;
import com.spring.foodapi.domain.repository.CidadeRepository;
import com.spring.foodapi.domain.repository.EstadoRepository;

@Service
public class CadastroCidadeService {
    
    @Autowired
    CidadeRepository cidadeRepository;

    @Autowired
    EstadoRepository estadoRepository;

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }

    public Cidade buscarPorId(Long cidadeId) {
        try {
            return cidadeRepository.findById(cidadeId).get();
        } catch (NoSuchElementException e) {
            throw new EntidadeNaoEncontradaException("Entidade cidade com id " + cidadeId + " não foi encotrado");
        }
    }

    public Cidade salvar(Cidade cidade) {
        cidade.setId(null);
        Long estadoId = cidade.getEstado().getId();
        try {
            Estado estado = estadoRepository.findById(estadoId).get();
            cidade.setEstado(estado);
            cidade = cidadeRepository.save(cidade);
        } catch (NoSuchElementException e) {
            throw new EntidadeNaoEncontradaException("Entidade estado com id " + estadoId +" não foi encontrda");
        }
        
        return cidade;
    }

    public Cidade atualizar(Cidade cidade, Long cidadeId) {
        Long estadoId = cidade.getEstado().getId();
        Cidade cidadeAtual = cidadeRepository.findById(cidadeId).get();
        Estado estado = estadoRepository.findById(estadoId).get();

        if (cidade.getNome() != null && !cidade.getNome().trim().isEmpty()){
            cidadeAtual.setNome(cidade.getNome());
        }

        cidadeAtual.setEstado(estado);

        return cidadeRepository.save(cidadeAtual);

    }

    public void deletar(Long cidadeId) {
        try {
            estadoRepository.deleteById(cidadeId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntidadeNaoEncontradaException("");
        }
    }
}
