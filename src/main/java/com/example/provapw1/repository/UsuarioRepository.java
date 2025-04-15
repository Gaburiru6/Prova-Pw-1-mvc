package com.example.provapw1.repository;

import com.example.provapw1.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByEmail(String email); // Para verificar e-mail duplicado
}
