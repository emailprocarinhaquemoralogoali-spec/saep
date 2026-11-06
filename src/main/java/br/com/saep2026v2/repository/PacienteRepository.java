package br.com.saep2026v2.repository;

import br.com.saep2026v2.model.Paciente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PacienteRepository extends JpaRepository<Paciente, Long> {
}
