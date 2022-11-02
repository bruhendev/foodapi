package com.spring.foodapi.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.spring.foodapi.domain.exception.EntidadeEmUsoException;
import com.spring.foodapi.domain.exception.EntidadeNaoEncontradaException;
import com.spring.foodapi.domain.model.Cozinha;
import com.spring.foodapi.domain.repository.CozinhaRepository;
import com.spring.foodapi.domain.service.CadastroCozinhaService;

import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/cozinhas")
public class CozinhaController {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private CadastroCozinhaService cadastroCozinha;

    @ResponseStatus(HttpStatus.OK)
    @GetMapping
    public List<Cozinha> listar() {
        return cozinhaRepository.findAll();
    }

    @GetMapping("/{cozinhaId}")
    public ResponseEntity<Cozinha> buscar(@PathVariable("cozinhaId") Long id) {

        try {
            Cozinha cozinha = cozinhaRepository.findById(id).get();
            return ResponseEntity.status(HttpStatus.OK).body(cozinha);
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }

        // HttpHeaders headers = new HttpHeaders();
        // headers.add(HttpHeaders.LOCATION, "http://localhost:8080/cozinhas");

        // return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cozinha adicionar(@RequestBody Cozinha cozinha) {
        return cadastroCozinha.salvar(cozinha);
    }

    @PutMapping(value = "{cozinhaId}")
    public ResponseEntity<Cozinha> atualizar(@PathVariable Long cozinhaId, @RequestBody Cozinha cozinha) {
        Cozinha cozinhaAtual = cozinhaRepository.findById(cozinhaId).get();

        // BeanUtils.copyProperties(cozinha, cozinhaAtual, "id");
        if (cozinha.getNome() != null && !cozinha.getNome().trim().isEmpty()) {
            cozinhaAtual.setNome(cozinha.getNome());
        }

        cozinhaRepository.save(cozinhaAtual);

        return ResponseEntity.ok(cozinhaAtual);
    }

    @DeleteMapping("/{conzinhaId}")
    public ResponseEntity<?> remover(@PathVariable Long conzinhaId) {
        try {
            cadastroCozinha.excluir(conzinhaId);
            return ResponseEntity.noContent().build();
        } catch (EntidadeEmUsoException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        } catch (EntidadeNaoEncontradaException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
        }
    }

}
