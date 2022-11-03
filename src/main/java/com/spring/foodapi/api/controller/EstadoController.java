package com.spring.foodapi.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.foodapi.domain.model.Estado;
import com.spring.foodapi.domain.service.CadastroEstadoService;

@RestController
@RequestMapping("/estados")
public class EstadoController {
    
    @Autowired
    CadastroEstadoService cadastroEstado;

    @GetMapping
    public ResponseEntity<List<Estado>> listar() {
        return ResponseEntity.ok(cadastroEstado.listar());
    }

    @GetMapping("/{estadoId}")
    public ResponseEntity<List<Estado>> buscar(@PathVariable Long estadoId) {
        return ResponseEntity.ok(cadastroEstado.listar());
    }
}
