package br.com.raaydesenvolvimento.api.service;

import br.com.raaydesenvolvimento.api.dto.request.AtualizarUsuarioDTO;
import br.com.raaydesenvolvimento.api.dto.request.CriarUsuarioDTO;
import br.com.raaydesenvolvimento.api.dto.response.UsuarioDTO;
import br.com.raaydesenvolvimento.api.mapper.UsuarioMapper;
import br.com.raaydesenvolvimento.api.model.Perfil;
import br.com.raaydesenvolvimento.api.model.Usuario;
import br.com.raaydesenvolvimento.api.repository.PerfilRepository;
import br.com.raaydesenvolvimento.api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsuarioServiceTests {

    @Mock
    private UsuarioRepository usuarioRepository;
    @Mock
    private PerfilRepository perfilRepository;
    @Mock
    private UsuarioMapper usuarioMapper;
    @InjectMocks
    private UsuarioService usuarioService;


    private UUID id;
    private Usuario usuario;
    private UsuarioDTO usuarioDTO;
    private Perfil perfil;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();
        perfil = new Perfil();
        perfil.setId(UUID.randomUUID());
        perfil.setDescricao("Perfil Teste");
        usuario = new Usuario();
        usuario.setId(id);
        usuario.setNome("Usuário Teste");
        usuario.setPerfis(Set.of(perfil));
        usuarioDTO = new UsuarioDTO(id, "Usuário Teste", Set.of());
    }

    @Test
    void deveSalvarUsuario() {
        when(perfilRepository.findAllById(any())).thenReturn(List.of(perfil));
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(usuarioMapper.toDto(usuario)).thenReturn(usuarioDTO);

        CriarUsuarioDTO dto = new CriarUsuarioDTO("Usuário Teste", Set.of(id));
        UsuarioDTO salvo = usuarioService.salvar(dto);

        assertThat(salvo.nome()).isEqualTo("Usuário Teste");
    }

    @Test
    void deveAtualizarUsuario() {
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(perfilRepository.findAllById(any())).thenReturn(List.of(perfil));
        when(usuarioRepository.save(any())).thenReturn(usuario);
        when(usuarioMapper.toDto(usuario)).thenReturn(new UsuarioDTO(id, "Nome Atualizado", Set.of()));

        AtualizarUsuarioDTO dto = new AtualizarUsuarioDTO("Nome Atualizado", Set.of(id));
        UsuarioDTO atualizado = usuarioService.atualizar(id, dto);

        assertThat(atualizado.nome()).isEqualTo("Nome Atualizado");
    }

    @Test
    void deveLancarExcecaoAoAtualizarUsuarioInexistente() {
        UUID idInvalido = UUID.randomUUID();
        when(usuarioRepository.findById(idInvalido)).thenReturn(Optional.empty());

        AtualizarUsuarioDTO dto = new AtualizarUsuarioDTO("Falha", Set.of());

        assertThatThrownBy(() -> usuarioService.atualizar(idInvalido, dto))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    void deveListarUsuariosComFiltro() {
        when(usuarioRepository.findByNomeContainingIgnoreCase(eq("Teste"), any())).thenReturn(new PageImpl<>(List.of(usuario)));
        when(usuarioMapper.toDto(usuario)).thenReturn(usuarioDTO);

        Page<UsuarioDTO> resultado = usuarioService.listar("Teste", PageRequest.of(0, 10));
        assertThat(resultado.getContent()).hasSize(1);
        assertThat(resultado.getContent().get(0).nome()).isEqualTo("Usuário Teste");
    }

    @Test
    void deveDetalharUsuario() {
        when(usuarioRepository.findById(id)).thenReturn(Optional.of(usuario));
        when(usuarioMapper.toDto(usuario)).thenReturn(usuarioDTO);

        UsuarioDTO encontrado = usuarioService.detalhar(id);
        assertThat(encontrado.nome()).isEqualTo("Usuário Teste");
    }

    @Test
    void deveLancarExcecaoAoDetalharUsuarioInexistente() {
        UUID idInvalido = UUID.randomUUID();
        when(usuarioRepository.findById(idInvalido)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> usuarioService.detalhar(idInvalido))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deveDeletarUsuario() {
        doNothing().when(usuarioRepository).deleteById(id);
        when(usuarioRepository.existsById(id)).thenReturn(true);
        usuarioService.deletar(id);
        verify(usuarioRepository).deleteById(id);
    }

    @Test
    void deveLancarExcecaoAoDeletarUsuarioInexistente() {
        UUID idInvalido = UUID.randomUUID();
        when(usuarioRepository.existsById(idInvalido)).thenReturn(false);

        assertThatThrownBy(() -> usuarioService.deletar(idInvalido))
                .isInstanceOf(EntityNotFoundException.class);
    }
}
