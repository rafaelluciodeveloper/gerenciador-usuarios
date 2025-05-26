package br.com.raaydesenvolvimento.api.controller;

import br.com.raaydesenvolvimento.api.dto.ApiError;
import br.com.raaydesenvolvimento.api.dto.PageResponse;
import br.com.raaydesenvolvimento.api.dto.request.AtualizarUsuarioDTO;
import br.com.raaydesenvolvimento.api.dto.request.CriarUsuarioDTO;
import br.com.raaydesenvolvimento.api.dto.response.UsuarioDTO;
import br.com.raaydesenvolvimento.api.exception.ValidationErrorResponse;
import br.com.raaydesenvolvimento.api.service.UsuarioService;
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

@Tag(name = "Usuarios", description = "A API de usuários permite gerenciar usuários do sistema.")
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Operation(summary = "Criar novo usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Usuário criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para criação do usuário",
                    content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@RequestBody @Valid CriarUsuarioDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.salvar(dto));
    }

    @Operation(summary = "Atualizar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos para atualização" ,  content = @Content(schema = @Schema(implementation = ValidationErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",  content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable UUID id, @RequestBody @Valid AtualizarUsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.atualizar(id, dto));
    }

    @Operation(summary = "Listar usuários com filtro e paginação")
    @GetMapping
    public ResponseEntity<PageResponse<UsuarioDTO>> listar(@RequestParam(defaultValue = "") String nome, @ParameterObject Pageable pageable) {
        var page = usuarioService.listar(nome, pageable);
        var response = new PageResponse<>(page.getContent(), page.getTotalPages(), page.getTotalElements());
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obter detalhes de um usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Usuário encontrado"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado",
                    content = @Content(schema = @Schema(implementation = ApiError.class)))})
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> detalhar(@PathVariable UUID id) {
        return ResponseEntity.ok(usuarioService.detalhar(id));
    }

    @Operation(summary = "Deletar usuário")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Usuário deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content(schema = @Schema(implementation = ApiError.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable UUID id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }

}
