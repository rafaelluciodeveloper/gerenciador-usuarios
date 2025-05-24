package br.com.raaydesenvolvimento.api.exception;

import br.com.raaydesenvolvimento.api.controller.PerfilController;
import br.com.raaydesenvolvimento.api.dto.ApiError;
import br.com.raaydesenvolvimento.api.service.PerfilService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = PerfilController.class)
@Import({GlobalExceptionHandler.class, GlobalExceptionHandlerTests.MockConfig.class})
class GlobalExceptionHandlerTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PerfilService perfilService;

    @Autowired
    private ObjectMapper objectMapper;

    @Configuration
    static class MockConfig {
        @Bean
        PerfilService perfilService() {
            return mock(PerfilService.class);
        }
    }

    @BeforeEach
    void resetMocks() {
        reset(perfilService);
    }

    @Test
    void deveRetornar500QuandoExcecaoGenerica() throws Exception {
        UUID id = UUID.randomUUID();
        when(perfilService.detalhar(id)).thenThrow(new RuntimeException("Falha"));

        mockMvc.perform(get("/api/perfis/" + id))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.status").value(500))
                .andExpect(jsonPath("$.mensagem").value("Erro interno: No static resource api/perfis/" + id + "."));
    }

    @Test
    void deveInstanciarApiError() {
        ApiError error = new ApiError(400, "msg", "/uri");
        assertThat(error.getStatus()).isEqualTo(400);
        assertThat(error.getMensagem()).isEqualTo("msg");
    }
}