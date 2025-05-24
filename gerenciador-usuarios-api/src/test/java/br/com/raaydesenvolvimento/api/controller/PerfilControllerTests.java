package br.com.raaydesenvolvimento.api.controller;

import br.com.raaydesenvolvimento.api.dto.request.AtualizarPerfilDTO;
import br.com.raaydesenvolvimento.api.dto.request.CriarPerfilDTO;
import br.com.raaydesenvolvimento.api.dto.response.PerfilDTO;
import br.com.raaydesenvolvimento.api.service.PerfilService;
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
import java.util.UUID;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = PerfilController.class)
@Import(PerfilControllerTests.TestConfig.class)
class PerfilControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private PerfilService perfilService;

    @BeforeEach
    void setup() {
        reset(perfilService);
    }

    @Test
    void deveListarPerfisComPaginacao() throws Exception {
        PerfilDTO dto1 = new PerfilDTO(UUID.randomUUID(), "Administrador");
        PerfilDTO dto2 = new PerfilDTO(UUID.randomUUID(), "Gerente");
        Page<PerfilDTO> page = new PageImpl<>(List.of(dto1, dto2), PageRequest.of(0, 2), 2);

        when(perfilService.listar(eq("a"), any())).thenReturn(page);

        mockMvc.perform(get("/api/perfis")
                        .param("descricao", "a")
                        .param("page", "0")
                        .param("size", "2")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.totalPages").value(1))
                .andExpect(jsonPath("$.totalElements").value(2))
                .andExpect(jsonPath("$.content[0].descricao").value("Administrador"));
    }

    @Test
    void deveDetalharPerfil() throws Exception {
        UUID id = UUID.randomUUID();
        PerfilDTO dto = new PerfilDTO(id, "Administrador");
        when(perfilService.detalhar(id)).thenReturn(dto);

        mockMvc.perform(get("/api/perfis/" + id)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(id.toString()))
                .andExpect(jsonPath("$.descricao").value("Administrador"));
    }

    @Test
    void deveCriarPerfil() throws Exception {
        CriarPerfilDTO dto = new CriarPerfilDTO("Administrador do sistema");

        mockMvc.perform(post("/api/perfis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Perfil criado com sucesso."));
    }

    @Test
    void naoDeveCriarPerfilComDescricaoVazia() throws Exception {
        CriarPerfilDTO dto = new CriarPerfilDTO("");

        mockMvc.perform(post("/api/perfis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("descricao"));
    }

    @Test
    void naoDeveCriarPerfilComDescricaoCurta() throws Exception {
        CriarPerfilDTO dto = new CriarPerfilDTO("abc");

        mockMvc.perform(post("/api/perfis")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("descricao"));
    }

    @Test
    void deveAtualizarPerfil() throws Exception {
        UUID id = UUID.randomUUID();
        AtualizarPerfilDTO dto = new AtualizarPerfilDTO("Perfil Atualizado");

        mockMvc.perform(patch("/api/perfis/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(content().string("Perfil atualizado com sucesso."));
    }

    @Test
    void naoDeveAtualizarPerfilComDescricaoVazia() throws Exception {
        UUID id = UUID.randomUUID();
        AtualizarPerfilDTO dto = new AtualizarPerfilDTO("");

        mockMvc.perform(patch("/api/perfis/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors[0].field").value("descricao"));
    }

    @Test
    void deveDeletarPerfil() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/api/perfis/" + id))
                .andExpect(status().isOk())
                .andExpect(content().string("Perfil deletado com sucesso."));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PerfilService perfilService() {
            return mock(PerfilService.class);
        }
    }
}
