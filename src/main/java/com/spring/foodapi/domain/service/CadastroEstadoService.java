package com.spring.foodapi.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spring.foodapi.domain.model.Estado;
import com.spring.foodapi.domain.repository.EstadoRepository;

@Service
public class CadastroEstadoService {
    
    @Autowired
    EstadoRepository estadoRepository;

    public List<Estado> listar() {
        return estadoRepository.findAll();
    }
}
