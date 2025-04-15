package com.example.provapw1.service;

import com.example.provapw1.Usuario;
import com.example.provapw1.dto.CadastroDTO;
import com.example.provapw1.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public String cadastrarUsuario(CadastroDTO cadastroDTO) {
        if (usuarioRepository.findByEmail(cadastroDTO.getEmail()) != null) {
            return "E-mail já cadastrado!";
        }

        Usuario usuario = new Usuario();
        usuario.setNome(cadastroDTO.getNome());
        usuario.setEmail(cadastroDTO.getEmail());
        usuario.setSenha(cadastroDTO.getSenha()); // Em produção, use BCrypt

        usuarioRepository.save(usuario);
        return null; // Retorna null se sucesso
    }
}