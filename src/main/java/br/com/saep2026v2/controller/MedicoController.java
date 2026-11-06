package br.com.saep2026v2.controller;

import br.com.saep2026v2.model.Medico;
import br.com.saep2026v2.repository.MedicoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/medico")
public class MedicoController {

    private final MedicoRepository medicoRepository;

    public MedicoController(MedicoRepository medicoRepository) {
        this.medicoRepository = medicoRepository;
    }
    @GetMapping
    public String listar(Model model) {
        model.addAttribute("medicos", medicoRepository.findAll());
        return "medico/listagem";
    }

    @GetMapping({"/inserir", "/form_inserir"})
    public String abrirInserir(Model model) {
        model.addAttribute("medico", new Medico());
        return "medico/form_inserir";
    }

    @PostMapping("/inserir")
    public String salvar(@ModelAttribute Medico medico) {
        medicoRepository.save(medico);
        return "redirect:/medico";
    }

    @GetMapping("/alterar/{id}")
    public String abrirAlterar(@PathVariable Long id, Model model) {
        Medico medico = medicoRepository.findById(id).orElseThrow();
        model.addAttribute("medico", medico);
        return "medico/form_alterar";
    }

    @PostMapping("/alterar/{id}")
    public String alterar(@PathVariable Long id, @ModelAttribute Medico medico) {
        Medico existente = medicoRepository.findById(id).orElseThrow();
        existente.setNome(medico.getNome());
        existente.setCrm(medico.getCrm());
        existente.setEspecialidade(medico.getEspecialidade());
        medicoRepository.save(existente);
        return "redirect:/medico";
    }

    @GetMapping("/excluir/{id}")
    public String excluir(@PathVariable Long id) {
        medicoRepository.deleteById(id);
        return "redirect:/medico";
    }
}
