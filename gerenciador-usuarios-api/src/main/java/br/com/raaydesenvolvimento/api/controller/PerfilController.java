package br.com.raaydesenvolvimento.api.controller;

import br.com.raaydesenvolvimento.api.dto.ApiError;
import br.com.raaydesenvolvimento.api.dto.PageResponse;
import br.com.raaydesenvolvimento.api.dto.request.AtualizarPerfilDTO;
import br.com.raaydesenvolvimento.api.dto.request.CriarPerfilDTO;
import br.com.raaydesenvolvimento.api.dto.response.PerfilDTO;
import br.com.raaydesenvolvimento.api.exception.ValidationErrorResponse;
import br.com.raaydesenvolvimento.api.service.PerfilService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
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
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do perfil",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<PerfilDTO> criar(@RequestBody @Valid CriarPerfilDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(perfilService.salvar(dto));
    }

    @Operation(summary = "Atualizar perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<PerfilDTO> atualizar(@PathVariable UUID id, @RequestBody @Valid AtualizarPerfilDTO dto) {
        return ResponseEntity.ok(perfilService.atualizar(id, dto));
    }

    @Operation(summary = "Listar perfis com filtro e paginação")
    @GetMapping
    public ResponseEntity<PageResponse<PerfilDTO>> listar(@RequestParam(defaultValue = "") String descricao, @ParameterObject Pageable pageable) {
        var page = perfilService.listar(descricao, pageable);
        var response = new PageResponse<>(page.getContent(), page.getTotalPages(), page.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obter detalhes de um perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Perfil encontrado"),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<PerfilDTO> detalhar(@PathVariable UUID id) {
        return ResponseEntity.ok(perfilService.detalhar(id));
    }

    @Operation(summary = "Deletar perfil")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Perfil deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Perfil não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        perfilService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
