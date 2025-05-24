package br.com.raaydesenvolvimento.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CriarPerfilDTO(
        @NotBlank(message = "descricao é obrigatória")
        @Size(min = 5, message = "descricao deve ter pelo menos 5 caracteres")
        String descricao
) {}
