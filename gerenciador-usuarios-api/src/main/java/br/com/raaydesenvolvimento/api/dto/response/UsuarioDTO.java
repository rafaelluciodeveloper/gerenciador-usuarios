package br.com.raaydesenvolvimento.api.dto.response;

import java.util.Set;
import java.util.UUID;

public record UsuarioDTO(
        UUID id,
        String nome,
        Set<PerfilDTO> perfis
) {}