package br.com.raaydesenvolvimento.api.service;

import br.com.raaydesenvolvimento.api.dto.request.AtualizarPerfilDTO;
import br.com.raaydesenvolvimento.api.dto.request.CriarPerfilDTO;
import br.com.raaydesenvolvimento.api.dto.response.PerfilDTO;
import br.com.raaydesenvolvimento.api.mapper.PerfilMapper;
import br.com.raaydesenvolvimento.api.model.Perfil;
import br.com.raaydesenvolvimento.api.repository.PerfilRepository;
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

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PerfilServiceTests {

    @Mock
    private PerfilRepository perfilRepository;

    @Mock
    private PerfilMapper perfilMapper;

    @InjectMocks
    private PerfilService perfilService;

    private Perfil perfil;
    private PerfilDTO perfilDTO;
    private UUID id;

    @BeforeEach
    void setup() {
        id = UUID.randomUUID();
        perfil = new Perfil();
        perfil.setId(id);
        perfil.setDescricao("Perfil Teste");
        perfilDTO = new PerfilDTO(id, "Perfil Teste");
    }

    @Test
    void deveSalvarPerfil() {
        when(perfilRepository.save(any())).thenReturn(perfil);
        when(perfilMapper.toDto(perfil)).thenReturn(perfilDTO);

        CriarPerfilDTO dto = new CriarPerfilDTO("Perfil Teste");
        PerfilDTO salvo = perfilService.salvar(dto);

        assertThat(salvo.descricao()).isEqualTo("Perfil Teste");
    }

    @Test
    void deveAtualizarPerfil() {
        when(perfilRepository.findById(id)).thenReturn(Optional.of(perfil));
        when(perfilRepository.save(perfil)).thenReturn(perfil);
        when(perfilMapper.toDto(perfil)).thenReturn(new PerfilDTO(id, "Perfil Atualizado"));

        AtualizarPerfilDTO dto = new AtualizarPerfilDTO("Perfil Atualizado");
        PerfilDTO atualizado = perfilService.atualizar(id, dto);

        assertThat(atualizado.descricao()).isEqualTo("Perfil Atualizado");
    }

    @Test
    void deveListarPerfisComPaginacao() {
        Page<Perfil> pagina = new PageImpl<>(List.of(perfil));
        when(perfilRepository.findByDescricaoContainingIgnoreCase(eq("Teste"), any())).thenReturn(pagina);
        when(perfilMapper.toDto(perfil)).thenReturn(perfilDTO);

        Page<PerfilDTO> resultado = perfilService.listar("Teste", PageRequest.of(0, 10));

        assertThat(resultado.getContent()).hasSize(1);
        assertThat(resultado.getContent().get(0).descricao()).isEqualTo("Perfil Teste");
    }

    @Test
    void deveDetalharPerfil() {
        when(perfilRepository.findById(id)).thenReturn(Optional.of(perfil));
        when(perfilMapper.toDto(perfil)).thenReturn(perfilDTO);

        PerfilDTO encontrado = perfilService.detalhar(id);
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.id()).isEqualTo(id);
    }

    @Test
    void deveLancarExcecaoAoDetalharPerfilInexistente() {
        UUID idInvalido = UUID.randomUUID();
        when(perfilRepository.findById(idInvalido)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> perfilService.detalhar(idInvalido))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void deveDeletarPerfil() {
        doNothing().when(perfilRepository).deleteById(id);
        when(perfilRepository.existsById(id)).thenReturn(true);
        perfilService.deletar(id);
        verify(perfilRepository).deleteById(id);
    }

    @Test
    void deveLancarExcecaoAoDeletarPerfilInexistente() {
        UUID idInvalido = UUID.randomUUID();
        when(perfilRepository.existsById(idInvalido)).thenReturn(false);

        assertThatThrownBy(() -> perfilService.deletar(idInvalido))
                .isInstanceOf(EntityNotFoundException.class);
    }
}