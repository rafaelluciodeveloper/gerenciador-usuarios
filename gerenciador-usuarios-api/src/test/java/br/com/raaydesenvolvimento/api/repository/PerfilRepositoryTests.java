package br.com.raaydesenvolvimento.api.repository;

import br.com.raaydesenvolvimento.api.model.Perfil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class PerfilRepositoryTests {
    @Autowired
    private PerfilRepository perfilRepository;

    @Test
    void deveSalvarPerfil() {
        Perfil perfil = new Perfil();
        perfil.setDescricao("Perfil de Teste");

        Perfil salvo = perfilRepository.save(perfil);
        assertThat(salvo).isNotNull();
        assertThat(salvo.getDescricao()).isEqualTo("Perfil de Teste");
    }

    @Test
    void deveBuscarPorIdExistente() {
        Perfil perfil = new Perfil();
        perfil.setDescricao("Buscar Teste");
        perfilRepository.save(perfil);

        Optional<Perfil> encontrado = perfilRepository.findById(perfil.getId());
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getDescricao()).isEqualTo("Buscar Teste");
    }

    @Test
    void naoDeveEncontrarPerfilComIdInexistente() {
        Optional<Perfil> resultado = perfilRepository.findById(UUID.randomUUID());
        assertThat(resultado).isNotPresent();
    }

    @Test
    void deveFiltrarDescricaoComPaginacao() {
        Perfil perfil = new Perfil();
        perfil.setDescricao("Filtro Teste");
        perfilRepository.save(perfil);

        Page<Perfil> pagina = perfilRepository.findByDescricaoContainingIgnoreCase("Filtro", PageRequest.of(0, 10));
        assertThat(pagina.getContent()).extracting(Perfil::getDescricao).contains("Filtro Teste");
    }

    @Test
    void deveRetornarPaginaVaziaAoFiltrarSemResultado() {
        Page<Perfil> pagina = perfilRepository.findByDescricaoContainingIgnoreCase("Inexistente", PageRequest.of(0, 10));
        assertThat(pagina.getContent()).isEmpty();
    }

    @Test
    void deveDeletarPorIdExistente() {
        Perfil perfil = new Perfil();
        perfil.setDescricao("Excluir Teste");
        perfilRepository.save(perfil);

        perfilRepository.deleteById(perfil.getId());
        assertThat(perfilRepository.findById(perfil.getId())).isNotPresent();
    }
}
