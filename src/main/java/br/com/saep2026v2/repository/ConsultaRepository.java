package br.com.saep2026v2.repository;

import br.com.saep2026v2.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    @Query("""
        select c
        from Consulta c
        order by day(c.dataConsulta), c.dataConsulta
    """)
    List<Consulta> listarOrdenadoPorDia();

}