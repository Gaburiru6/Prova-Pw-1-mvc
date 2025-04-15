package com.example.provapw1.controller;

import com.example.provapw1.dto.CadastroDTO;
import com.example.provapw1.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CadastroController {
    @Autowired
    private UsuarioService usuarioService;

    // Exibe o formulário (GET /cadastro)
    @GetMapping("/cadastro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("cadastroDTO", new CadastroDTO());
        return "cadastro";
    }

    // Processa o cadastro (POST /cadastro)
    @PostMapping("/cadastro")
    public String processarCadastro(
            @Valid @ModelAttribute("cadastroDTO") CadastroDTO cadastroDTO,
            BindingResult result,
            RedirectAttributes redirectAttributes,
            HttpSession session
    ) {
        if (result.hasErrors()) {
            return "cadastro"; // Validação falhou
        }

        String erro = usuarioService.cadastrarUsuario(cadastroDTO);
        if (erro != null) {
            redirectAttributes.addFlashAttribute("erro", erro);
            return "redirect:/cadastro"; // E-mail duplicado
        }

        // Cria sessão manualmente (simplificado)
        session.setAttribute("usuarioLogado", cadastroDTO.getEmail());
        return "redirect:/dashboard"; // Sucesso
    }

    // Página após login (GET /dashboard)
    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        if (session.getAttribute("usuarioLogado") == null) {
            return "redirect:/cadastro"; // Bloqueia acesso não autenticado
        }
        return "dashboard";
    }
}
