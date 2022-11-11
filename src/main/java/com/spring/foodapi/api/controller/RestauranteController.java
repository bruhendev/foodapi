package com.spring.foodapi.api.controller;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.spring.foodapi.domain.model.Restaurante;
import com.spring.foodapi.domain.repository.RestauranteRepository;
import com.spring.foodapi.domain.service.CadastroRestauranteService;


@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private CadastroRestauranteService cadastroRestaurante;

    @Autowired
    private RestauranteRepository restauranteRepository;

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

        return atualizar(restauranteAtual, restaurnateId);
    }

    private void merge(Map<String, Object> dadosOrigem, Restaurante restauranteDestino) {
        ObjectMapper objectMapper = new ObjectMapper();
        Restaurante restauranteOrigem = objectMapper.convertValue(dadosOrigem, Restaurante.class);

        dadosOrigem.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            if (field != null)
                field.setAccessible(true);

            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restauranteDestino, novoValor);
        });
    }

    @GetMapping("/por-taxa-frete")
    private ResponseEntity<List<Restaurante>> restaurantesPorTaxaFrete(BigDecimal taxaInicial, BigDecimal taxaFinal) {
        return ResponseEntity.ok(restauranteRepository.taxaFreteBetween(taxaInicial, taxaFinal));
    }

    @GetMapping("/por-nome")
    private ResponseEntity<List<Restaurante>> porNome(String nome, Long restaurante) {

        System.out.println("---------" + nome + "----------" + restaurante);
        return ResponseEntity.ok(restauranteRepository.consultarPorNome(nome, restaurante));
    }

    @GetMapping("/por-nome-e-frete")
    private ResponseEntity<List<Restaurante>> restaurantesPorNomeFrete(String nome, BigDecimal taxaFreteInicial,
            BigDecimal taxaFreteFinal) {
        return ResponseEntity.ok(restauranteRepository.find(nome, taxaFreteInicial, taxaFreteFinal));
    }

    @GetMapping("/com-frete-gratis")
    private ResponseEntity<List<Restaurante>> restaurantesComFreteGratis(String nome) {
        
        return ResponseEntity.ok(restauranteRepository.findComFreteGratis(nome));
    }

}
