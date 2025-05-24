package br.com.raaydesenvolvimento.api.mapper;

import br.com.raaydesenvolvimento.api.dto.response.PerfilDTO;
import br.com.raaydesenvolvimento.api.model.Perfil;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface  PerfilMapper {
    PerfilDTO toDto(Perfil perfil);
}