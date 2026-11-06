package br.com.saep2026v2.repository;

import br.com.saep2026v2.model.Responsavel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ResponsavelRepository extends JpaRepository<Responsavel, Long> {
    List<Responsavel> findByNomeContainingIgnoreCase(String nome);
}
