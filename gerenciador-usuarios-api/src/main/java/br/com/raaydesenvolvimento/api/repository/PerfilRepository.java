package br.com.raaydesenvolvimento.api.repository;

import br.com.raaydesenvolvimento.api.model.Perfil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PerfilRepository extends JpaRepository<Perfil, UUID> {
    Page<Perfil> findByDescricaoContainingIgnoreCase(String descricao, Pageable pageable);
}
