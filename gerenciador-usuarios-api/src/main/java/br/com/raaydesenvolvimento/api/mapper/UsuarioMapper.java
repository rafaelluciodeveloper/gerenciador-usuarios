package br.com.raaydesenvolvimento.api.mapper;

import br.com.raaydesenvolvimento.api.dto.response.UsuarioDTO;
import br.com.raaydesenvolvimento.api.model.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {PerfilMapper.class})
public interface  UsuarioMapper {
    UsuarioDTO toDto(Usuario usuario);
}
