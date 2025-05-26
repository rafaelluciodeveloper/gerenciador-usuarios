package br.com.raaydesenvolvimento.api.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ApiError {
    private LocalDateTime timestamp = LocalDateTime.now();
    private int status;
    private String message;
    private String path;

    public ApiError(int status, String message, String path) {
        this.status = status;
        this.message = message;
        this.path = path;
    }
}