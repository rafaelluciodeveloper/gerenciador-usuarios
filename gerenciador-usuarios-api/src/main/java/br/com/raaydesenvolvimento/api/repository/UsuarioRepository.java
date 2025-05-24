package br.com.raaydesenvolvimento.api.repository;

import br.com.raaydesenvolvimento.api.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, UUID> {
    Page<Usuario> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
}
