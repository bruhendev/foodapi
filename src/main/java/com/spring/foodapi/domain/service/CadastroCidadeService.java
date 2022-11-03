package com.spring.foodapi.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.foodapi.domain.model.Cidade;
import com.spring.foodapi.domain.repository.CidadeRepository;

@Service
public class CadastroCidadeService {
    
    @Autowired
    CidadeRepository cidadeRepository;

    public List<Cidade> listar() {
        return cidadeRepository.findAll();
    }
}
