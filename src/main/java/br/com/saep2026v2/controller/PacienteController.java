package br.com.saep2026v2.controller;

import br.com.saep2026v2.model.Paciente;
import br.com.saep2026v2.repository.PacienteRepository;
import br.com.saep2026v2.repository.ResponsavelRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/paciente")
public class PacienteController {
    private final PacienteRepository pacienteRepository;
    private final ResponsavelRepository responsavelRepository;

    public PacienteController(PacienteRepository pacienteRepository, ResponsavelRepository responsavelRepository) {
        this.pacienteRepository = pacienteRepository;
        this.responsavelRepository = responsavelRepository;
    }

    @GetMapping
    public String listar(Model model) {
        model.addAttribute("pacientes", pacienteRepository.findAll());
        return "paciente/listagem";
    }

    @GetMapping({"/inserir", "/form_inserir"})
    public String abrirInserir(Model model) {
        model.addAttribute("paciente", new Paciente());
        model.addAttribute("responsaveis", responsavelRepository.findAll());
        return "paciente/form_inserir";
    }

    @PostMapping("/inserir")
    public String inserir(@ModelAttribute Paciente paciente,
                          @RequestParam Long responsavelId) {
        paciente.setResponsavel(responsavelRepository.findById(responsavelId).orElseThrow());
        pacienteRepository.save(paciente);
        return "redirect:/paciente";
    }

    @GetMapping("/alterar/{id}")
    public String abrirAlterar(@PathVariable Long id, Model model) {
        Paciente paciente = pacienteRepository.findById(id).orElseThrow();
        model.addAttribute("paciente", paciente);
        model.addAttribute("responsaveis", responsavelRepository.findAll());
        return "paciente/form_alterar";
    }

    @PostMapping("/alterar/{id}")
    public String alterar(@PathVariable Long id,
                          @ModelAttribute Paciente paciente,
                          @RequestParam Long responsavelId) {
        Paciente existente = pacienteRepository.findById(id).orElseThrow();
        existente.setNome(paciente.getNome());
        existente.setDataNascimento(paciente.getDataNascimento());
        existente.setResponsavel(responsavelRepository.findById(responsavelId).orElseThrow());
        pacienteRepository.save(existente);
        return "redirect:/paciente";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        pacienteRepository.deleteById(id);
        return "redirect:/paciente";
    }
}
