package br.com.raaydesenvolvimento.api.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;
import java.util.UUID;

public record CriarUsuarioDTO(
        @NotBlank(message = "nome é obrigatório")
        @Size(min = 10 , message = "nome deve ter pelo menos 10 caracteres")
        String nome,

        @NotNull(message = "perfis é obrigatório")
        @Size(min = 1, message = "O Usuário deve ter pelo menos um perfil")
        Set<UUID> perfis
) {}
