package br.com.saep2026v2.controller;


import br.com.saep2026v2.model.Responsavel;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import br.com.saep2026v2.repository.ResponsavelRepository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/responsavel")
public class ResponsavelController {
    private final ResponsavelRepository repository;

    public ResponsavelController(ResponsavelRepository repository){
        this.repository = repository;
    }

    @GetMapping
    public String listarResponsaveis(
            @RequestParam(required = false, defaultValue = "") String nome,
            Model model){
        String nomeBusca = nome.trim();
        if (nomeBusca.isEmpty()) {
            model.addAttribute("responsaveis", repository.findAll());
        } else {
            model.addAttribute("responsaveis", repository.findByNomeContainingIgnoreCase(nomeBusca));
        }
        model.addAttribute("nomeBusca", nome);
        return "responsavel/listagem";
    }

    @GetMapping("/form_inserir")
    public String inserirResponsavel(Model model){
        model.addAttribute("responsavel", new Responsavel());
        return "responsavel/form_inserir";
    }

    @PostMapping("/inserir")
    public String salvarResponsavel(@ModelAttribute Responsavel responsavel){
        repository.save(responsavel);
        return "redirect:/responsavel";
    }

    @GetMapping("/editar/{id}")
    public String editarResponsavel(@PathVariable Long id, Model model){
        Responsavel responsavel = repository.findById(id).orElse(null);
        if(responsavel == null){
            return "redirect:/responsavel";
        }
        model.addAttribute("responsavel", responsavel);
        return "responsavel/form_alterar";
    }

    @PostMapping("/editar/{id}")
    public String atualizarResponsavel(@PathVariable Long id, @ModelAttribute Responsavel responsavel){
        Responsavel responsavelExistente = repository.findById(id).orElse(null);
        if(responsavelExistente == null){
            return "redirect:/responsavel";
        }
        responsavelExistente.setNome(responsavel.getNome());
        responsavelExistente.setTelefone(responsavel.getTelefone());
        responsavelExistente.setEmail(responsavel.getEmail());
        responsavelExistente.setCpf(responsavel.getCpf());
        repository.save(responsavelExistente);
        return "redirect:/responsavel";
    }

    @GetMapping("/excluir/{id}")
    public String excluirResponsavel(@PathVariable Long id){
        repository.deleteById(id);
        return "redirect:/responsavel";
    }
}
