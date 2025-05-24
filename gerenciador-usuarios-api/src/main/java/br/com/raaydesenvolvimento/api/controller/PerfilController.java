package br.com.raaydesenvolvimento.api.controller;

import br.com.raaydesenvolvimento.api.dto.PageResponse;
import br.com.raaydesenvolvimento.api.dto.request.AtualizarPerfilDTO;
import br.com.raaydesenvolvimento.api.dto.request.CriarPerfilDTO;
import br.com.raaydesenvolvimento.api.dto.response.PerfilDTO;
import br.com.raaydesenvolvimento.api.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "Perfis", description = "A API de perfis permite gerenciar perfis de usuários.")
@RestController
@RequestMapping("/api/perfis")
public class PerfilController {

    private final PerfilService perfilService;

    public PerfilController(PerfilService perfilService) {
        this.perfilService = perfilService;
    }

    @Operation(summary = "Criar novo perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Perfil criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do perfil")
    })
    @PostMapping
    public ResponseEntity<String> criar(@RequestBody @Valid CriarPerfilDTO dto) {
        perfilService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Perfil criado com sucesso.");
    }

    @Operation(summary = "Atualizar perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização"),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<String> atualizar(@PathVariable UUID id, @RequestBody @Valid AtualizarPerfilDTO dto) {
        perfilService.atualizar(id, dto);
        return ResponseEntity.ok("Perfil atualizado com sucesso.");
    }

    @Operation(summary = "Listar perfis com filtro e paginação")
    @GetMapping
    public ResponseEntity<PageResponse<PerfilDTO>> listar(@RequestParam(defaultValue = "") String descricao, @ParameterObject Pageable pageable) {
        var page = perfilService.listar(descricao, pageable);
        var response = new PageResponse<>(page.getContent(),page.getTotalPages(), page.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obter detalhes de um perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> detalhar(@PathVariable UUID id) {
        return ResponseEntity.ok(perfilService.detalhar(id));
    }

    @Operation(summary = "Deletar perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletar(@PathVariable UUID id) {
        perfilService.deletar(id);
        return ResponseEntity.ok("Perfil deletado com sucesso.");
    }

}
