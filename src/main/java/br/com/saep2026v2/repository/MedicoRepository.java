package br.com.saep2026v2.repository;

import br.com.saep2026v2.model.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicoRepository extends JpaRepository<Medico, Long> {
}
