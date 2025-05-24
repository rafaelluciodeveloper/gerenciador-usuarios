package br.com.raaydesenvolvimento.api.dto;

import java.util.List;

public record PageResponse<T>(List<T> content,int totalPages, long totalElements) {}

