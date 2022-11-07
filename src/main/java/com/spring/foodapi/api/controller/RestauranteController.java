package com.spring.foodapi.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.spring.foodapi.domain.model.Restaurante;
import com.spring.foodapi.domain.service.CadastroRestauranteService;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @GetMapping
    public ResponseEntity<List<Restaurante>> listar() {
        return ResponseEntity.ok(cadastroRestaurante.listar());
    }

    @GetMapping("/{restauranteId}")
    public ResponseEntity<Restaurante> buscar(@PathVariable("restauranteId") Long restauranteId) {
        try {
            return ResponseEntity.ok(cadastroRestaurante.buscar(restauranteId));
        } catch (EntidadeNaoEncontradaException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            restaurante = cadastroRestaurante.salvar(restaurante);
            return ResponseEntity.status(HttpStatus.CREATED).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
        }
    }

    @PutMapping("/{restauranteId}")
    public ResponseEntity<?> atualizar(@RequestBody Restaurante restaurante,
            @PathVariable("restauranteId") Long restauranteId) {
        try {
            restaurante = cadastroRestaurante.atualizar(restaurante, restauranteId);
            return ResponseEntity.status(HttpStatus.OK).body(restaurante);
        } catch (EntidadeNaoEncontradaException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @DeleteMapping("/{restauranteId}")
    public ResponseEntity<?> deletar(@PathVariable("restauranteId") Long restauranteId) {
        try {
            cadastroRestaurante.excluir(restauranteId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeNaoEncontradaException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }
    }

    @PatchMapping("/{restauranteId}")
    public ResponseEntity<?> atualizarParcial(@PathVariable("restauranteId") Long restaurnateId,
            @RequestBody Map<String, Object> campos) {
        
        Restaurante restauranteAtual = cadastroRestaurante.buscar(restaurnateId);

        merge(campos, restauranteAtual);

        return atualizar(restauranteAtual, restaurnateId)
    }

    private void merge(Map<String, Object> camposOrigem, Restaurante restauranteDestino){
        camposOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            System.out.println(nomePropriedade + " = " + valorPropriedade);
        });
    }

}
