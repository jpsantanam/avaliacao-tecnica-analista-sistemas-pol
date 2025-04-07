package com.jpsantana.Api.service;

import com.jpsantana.Api.repository.ProcessoRepository;
import com.jpsantana.Api.repository.ReuRepository;
import com.jpsantana.Api.model.Processo;
import com.jpsantana.Api.model.Reu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProcessoService {

    @Autowired
    private ProcessoRepository processoRepository;

    @Autowired
    private ReuRepository reuRepository;

    public Processo save(Processo Processo) {
        Optional<Processo> existingProcesso = processoRepository.findByNumero(Processo.getNumero());
        if (existingProcesso.isPresent()) {
            throw new IllegalArgumentException("Processo com o mesmo número já existe.");
        }
        return processoRepository.save(Processo);
    }

    public List<Processo> findAll() {
        return processoRepository.findAll();
    }

    public Optional<Processo> findById(Long id) {
        return processoRepository.findById(id);
    }

    public Processo update(Processo Processo) {
        return processoRepository.save(Processo);
    }

    public void deleteById(Long id) {
        processoRepository.deleteById(id);
    }

    public Processo adicionarReu(Long processoId, String nomeReu) {
        Optional<Processo> optional = processoRepository.findById(processoId);
        if (optional.isEmpty())
            return null;

        Processo processo = optional.get();
        Reu reu = new Reu();
        reu.setNome(nomeReu);
        reu.setProcesso(processo);
        processo.getReus().add(reu);

        reuRepository.save(reu); // salva o réu individualmente
        return processoRepository.save(processo); // atualiza o processo
    }

}