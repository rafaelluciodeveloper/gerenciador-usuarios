package br.com.raaydesenvolvimento.api.service;

import br.com.raaydesenvolvimento.api.dto.request.AtualizarPerfilDTO;
import br.com.raaydesenvolvimento.api.dto.request.CriarPerfilDTO;
import br.com.raaydesenvolvimento.api.dto.response.PerfilDTO;
import br.com.raaydesenvolvimento.api.mapper.PerfilMapper;
import br.com.raaydesenvolvimento.api.model.Perfil;
import br.com.raaydesenvolvimento.api.repository.PerfilRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PerfilService {

    private final PerfilRepository perfilRepository;
    private final PerfilMapper perfilMapper;

    public PerfilService(PerfilRepository perfilRepository, PerfilMapper perfilMapper) {
        this.perfilRepository = perfilRepository;
        this.perfilMapper = perfilMapper;
    }

    @Transactional
    public PerfilDTO salvar(CriarPerfilDTO dto) {
        Perfil perfil = new Perfil();
        perfil.setDescricao(dto.descricao());
        return perfilMapper.toDto(perfilRepository.save(perfil));
    }

    @Transactional
    public PerfilDTO atualizar(UUID id, AtualizarPerfilDTO dto) {
        Perfil perfil = perfilRepository.findById(id).orElseThrow();
        perfil.setDescricao(dto.descricao());
        return perfilMapper.toDto(perfilRepository.save(perfil));
    }

    @Transactional(readOnly = true)
    public Page<PerfilDTO> listar(String descricao, Pageable pageable) {
        return perfilRepository.findByDescricaoContainingIgnoreCase(descricao, pageable)
                .map(perfilMapper::toDto);
    }

    @Transactional(readOnly = true)
    public PerfilDTO detalhar(UUID id) {
        return perfilMapper.toDto(
                perfilRepository.findById(id)
                        .orElseThrow(() -> new EntityNotFoundException("Perfil com ID " + id + " não encontrado."))
        );
    }

    @Transactional
    public void deletar(UUID id) {
        if (!perfilRepository.existsById(id)) {
            throw new EntityNotFoundException("Perfil com ID " + id + " não encontrado.");
        }
        perfilRepository.deleteById(id);
    }
}