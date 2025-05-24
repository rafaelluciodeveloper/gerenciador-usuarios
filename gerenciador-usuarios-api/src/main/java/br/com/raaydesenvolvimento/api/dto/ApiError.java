package br.com.raaydesenvolvimento.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String mensagem;
    private String path;

    public ApiError(int status, String mensagem, String path) {
        this.status = status;
        this.mensagem = mensagem;
        this.path = path;
    }
}