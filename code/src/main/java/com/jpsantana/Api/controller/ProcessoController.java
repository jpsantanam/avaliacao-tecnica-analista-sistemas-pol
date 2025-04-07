package com.jpsantana.Api.controller;

import com.jpsantana.Api.model.Processo;
import com.jpsantana.Api.model.Reu;
import com.jpsantana.Api.service.ProcessoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/processos")
public class ProcessoController {

    @Autowired
    private ProcessoService processoService;

    @PostMapping
    public ResponseEntity create(@RequestBody Processo processo) {
        try {
            Processo existingProcesso = processoService.save(processo);
            return new ResponseEntity<>(existingProcesso, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<Processo>> getAll() {
        List<Processo> processos = processoService.findAll();
        return new ResponseEntity<>(processos, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Processo> getById(@PathVariable Long id) {
        Optional<Processo> processo = processoService.findById(id);
        return processo.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Processo> update(@PathVariable Long id, @RequestBody Processo processo) {
        if (!processoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        processo.setId(id);
        Processo updatedProcesso = processoService.update(processo);
        return new ResponseEntity<>(updatedProcesso, HttpStatus.OK);
    }

    @PostMapping("/{id}/reus")
    public ResponseEntity<Processo> adicionarReu(@PathVariable Long id, @RequestBody Reu reu) {
        if (reu.getNome() == null || reu.getNome().isEmpty()) {
            return ResponseEntity.badRequest().body(null);
        }
        Processo processo = processoService.adicionarReu(id, reu.getNome());
        if (processo == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(processo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        if (!processoService.findById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        processoService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
