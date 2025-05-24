package br.com.raaydesenvolvimento.api.dto.response;

import java.util.UUID;

public record PerfilDTO(
        UUID id,
        String descricao
) {}