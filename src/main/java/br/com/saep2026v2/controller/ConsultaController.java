package br.com.saep2026v2.controller;

import br.com.saep2026v2.model.Consulta;
import br.com.saep2026v2.repository.ConsultaRepository;
import br.com.saep2026v2.repository.PacienteRepository;
import br.com.saep2026v2.repository.MedicoRepository;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/consultas")
public class ConsultaController {

    private final ConsultaRepository consultaRepository;
    private final PacienteRepository pacienteRepository;
    private final MedicoRepository medicoRepository;

    public ConsultaController(
            ConsultaRepository consultaRepository,
            PacienteRepository pacienteRepository,
            MedicoRepository medicoRepository) {

        this.consultaRepository = consultaRepository;
        this.pacienteRepository = pacienteRepository;
        this.medicoRepository = medicoRepository;
    }

    @GetMapping
    public String listar(Model model) {

        model.addAttribute(
                "consultas",
                consultaRepository.listarOrdenadoPorDia()
        );

        return "consulta/listagem";
    }

    @GetMapping("/inserir")
    public String abrirInserir(Model model) {

        model.addAttribute("consulta", new Consulta());

        model.addAttribute(
                "pacientes",
                pacienteRepository.findAll()
        );

        model.addAttribute(
                "medicos",
                medicoRepository.findAll()
        );

        return "consulta/form_inserir";
    }

    @PostMapping("/salvar")
    public String salvar(@ModelAttribute Consulta consulta,
                         @RequestParam("pacienteId") Long pacienteId,
                         @RequestParam("medicoId") Long medicoId) {
        consulta.setPaciente(pacienteRepository.findById(pacienteId).orElseThrow());
        consulta.setMedico(medicoRepository.findById(medicoId).orElseThrow());
        consultaRepository.save(consulta);
        return "redirect:/consultas";
    }

    @GetMapping("/alterar/{id}")
    public String abrirAlterar(
            @PathVariable Long id,
            Model model) {

        model.addAttribute(
                "consulta",
                consultaRepository.findById(id).orElseThrow()
        );

        return "consulta/form_alterar";
    }

    @PostMapping("/alterar")
    public String alterar(@ModelAttribute Consulta consultaForm) {

        Consulta consultaBanco = consultaRepository
                .findById(consultaForm.getId())
                .orElseThrow();

        consultaBanco.setDataConsulta(
                consultaForm.getDataConsulta()
        );

        consultaRepository.save(consultaBanco);

        return "redirect:/consultas";
    }
}