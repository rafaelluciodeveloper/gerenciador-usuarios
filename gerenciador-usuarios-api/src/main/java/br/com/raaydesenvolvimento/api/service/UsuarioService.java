package br.com.raaydesenvolvimento.api.service;

import br.com.raaydesenvolvimento.api.dto.request.AtualizarUsuarioDTO;
import br.com.raaydesenvolvimento.api.dto.request.CriarUsuarioDTO;
import br.com.raaydesenvolvimento.api.dto.response.UsuarioDTO;
import br.com.raaydesenvolvimento.api.mapper.UsuarioMapper;
import br.com.raaydesenvolvimento.api.model.Usuario;
import br.com.raaydesenvolvimento.api.repository.PerfilRepository;
import br.com.raaydesenvolvimento.api.repository.UsuarioRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.UUID;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PerfilRepository perfilRepository;
    private final UsuarioMapper usuarioMapper;

    public UsuarioService(UsuarioRepository usuarioRepository, PerfilRepository perfilRepository, UsuarioMapper usuarioMapper) {
        this.usuarioRepository = usuarioRepository;
        this.perfilRepository = perfilRepository;
        this.usuarioMapper = usuarioMapper;
    }

    @Transactional
    public UsuarioDTO salvar(CriarUsuarioDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setNome(dto.nome());
        usuario.setPerfis(new HashSet<>(perfilRepository.findAllById(dto.perfis())));
        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioDTO atualizar(UUID id, AtualizarUsuarioDTO dto) {
        Usuario usuario = usuarioRepository.findById(id).orElseThrow();
        usuario.setNome(dto.nome());
        usuario.setPerfis(new HashSet<>(perfilRepository.findAllById(dto.perfis())));
        return usuarioMapper.toDto(usuarioRepository.save(usuario));
    }

    @Transactional(readOnly = true)
    public Page<UsuarioDTO> listar(String nome, Pageable pageable) {
        return usuarioRepository.findByNomeContainingIgnoreCase(nome, pageable).map(usuarioMapper::toDto);
    }

    @Transactional(readOnly = true)
    public UsuarioDTO detalhar(UUID id) {
        return usuarioMapper.toDto(usuarioRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Usuário com ID " + id + " não encontrado.")));
    }

    @Transactional
    public void deletar(UUID id) {
        if (!usuarioRepository.existsById(id)) {
            throw new EntityNotFoundException("Usuário com ID " + id + " não encontrado.");
        }
        usuarioRepository.deleteById(id);
    }
}