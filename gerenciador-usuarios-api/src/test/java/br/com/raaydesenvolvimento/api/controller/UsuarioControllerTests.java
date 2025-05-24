package br.com.raaydesenvolvimento.api.controller;

import br.com.raaydesenvolvimento.api.dto.request.AtualizarUsuarioDTO;
import br.com.raaydesenvolvimento.api.dto.request.CriarUsuarioDTO;
import br.com.raaydesenvolvimento.api.dto.response.UsuarioDTO;
import br.com.raaydesenvolvimento.api.service.UsuarioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = UsuarioController.class)
@Import(UsuarioControllerTests.TestConfig.class)
class UsuarioControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UsuarioService usuarioService;

    @BeforeEach
    void setup() {
        reset(usuarioService);
    }

    @Test
    void deveListarUsuariosComPaginacao() throws Exception {
        UsuarioDTO u1 = new UsuarioDTO(UUID.randomUUID(), "Rafael Lucio", Set.of());
        UsuarioDTO u2 = new UsuarioDTO(UUID.randomUUID(), "Rodrigo Maxwell", Set.of());
        Page<UsuarioDTO> page = new PageImpl<>(List.of(u1, u2), PageRequest.of(0, 2), 2);

        when(usuarioService.listar(eq("l"), any())).thenReturn(page);

        mockMvc.perform(get("/api/usuarios")
                        .param("nome", "l")
                        .param("page", "0")
                        .param("size", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content[1].nome").value("Rodrigo Maxwell"));
    }

    @Test
    void deveDetalharUsuario() throws Exception {
        UUID id = UUID.randomUUID();
        UsuarioDTO dto = new UsuarioDTO(id, "Rafael Lucio", Set.of());
        when(usuarioService.detalhar(id)).thenReturn(dto);

        mockMvc.perform(get("/api/usuarios/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.nome").value("Rafael Lucio"));
    }

    @Test
    void deveCriarUsuario() throws Exception {
        CriarUsuarioDTO dto = new CriarUsuarioDTO("João da silva lucio", Set.of(UUID.randomUUID()));

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Usuário criado com sucesso."));
    }

    @Test
    void naoDeveCriarUsuarioComNomeVazio() throws Exception {
        CriarUsuarioDTO dto = new CriarUsuarioDTO("", Set.of(UUID.randomUUID()));

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("nome"));
    }

    @Test
    void naoDeveCriarUsuarioSemPerfis() throws Exception {
        CriarUsuarioDTO dto = new CriarUsuarioDTO("Rafael da silva lucio", Set.of());

        mockMvc.perform(post("/api/usuarios")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("perfis"));
    }

    @Test
    void deveAtualizarUsuario() throws Exception {
        UUID id = UUID.randomUUID();
        AtualizarUsuarioDTO dto = new AtualizarUsuarioDTO("João Atualizado", Set.of(UUID.randomUUID()));

        mockMvc.perform(patch("/api/usuarios/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário atualizado com sucesso."));
    }

    @Test
    void naoDeveAtualizarUsuarioComNomeVazio() throws Exception {
        UUID id = UUID.randomUUID();
        AtualizarUsuarioDTO dto = new AtualizarUsuarioDTO("", Set.of(UUID.randomUUID()));

        mockMvc.perform(patch("/api/usuarios/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("nome"));
    }

    @Test
    void deveDeletarUsuario() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/usuarios/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("Usuário deletado com sucesso."));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UsuarioService usuarioService() {
            return mock(UsuarioService.class);
        }
    }
}