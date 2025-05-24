package br.com.raaydesenvolvimento.api.repository;

import br.com.raaydesenvolvimento.api.model.Perfil;
import br.com.raaydesenvolvimento.api.model.Usuario;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.Optional;
import java.util.UUID;

@DataJpaTest
class UsuarioRepositoryTests {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PerfilRepository perfilRepository;

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        usuario = new Usuario();
        usuario.setNome("Usuario de Teste");
        Perfil perfil = new Perfil();
        perfil.setDescricao("Perfil de Teste");
        perfilRepository.save(perfil);
        usuario.getPerfis().add(perfil);
        usuarioRepository.save(usuario);
    }

    @Test
    void deveSalvarUsuario() {
        usuario = new Usuario();
        usuario.setNome("Usuario de Teste");
        Perfil perfil = new Perfil();
        perfil.setDescricao("Perfil de Teste");
        perfilRepository.save(perfil);
        usuario.getPerfis().add(perfil);
        Usuario salvo = usuarioRepository.save(usuario);

        Assertions.assertThat(salvo).isNotNull();
        Assertions.assertThat(salvo.getNome()).isEqualTo("Usuario de Teste");
    }

    @Test
    void deveBuscarPorIdExistente() {
        Optional<Usuario> encontrado = usuarioRepository.findById(usuario.getId());
        Assertions.assertThat(encontrado).isPresent();
        Assertions.assertThat(encontrado.get().getNome()).isEqualTo("Usuario de Teste");
    }

    @Test
    void naoDeveEncontrarUsuarioComIdInexistente() {
        Optional<Usuario> resultado = usuarioRepository.findById(UUID.randomUUID());
        Assertions.assertThat(resultado).isNotPresent();
    }

    @Test
    void deveFiltrarDescricaoComPaginacao() {
        Page<Usuario> pagina = usuarioRepository.findByNomeContainingIgnoreCase("Teste", PageRequest.of(0, 10));
        Assertions.assertThat(pagina.getContent()).extracting(Usuario::getNome).contains("Usuario de Teste");
    }

    @Test
    void deveRetornarPaginaVaziaAoFiltrarSemResultado() {
        Page<Usuario> pagina = usuarioRepository.findByNomeContainingIgnoreCase("Inexistente", PageRequest.of(0, 10));
        Assertions.assertThat(pagina.getContent()).isEmpty();
    }

    @Test
    void deveDeletarPorIdExistente() {
        perfilRepository.deleteById(usuario.getId());
        Assertions.assertThat(perfilRepository.findById(usuario.getId())).isNotPresent();
    }
}
