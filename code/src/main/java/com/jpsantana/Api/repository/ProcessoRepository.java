package com.jpsantana.Api.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.jpsantana.Api.model.Processo;

public interface ProcessoRepository extends JpaRepository<Processo, Long> {
    Optional<Processo> findByNumero(String numero);
}
