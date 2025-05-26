package br.com.raaydesenvolvimento.api.exception;

import java.util.List;

public record ValidationErrorResponse(List<FieldErrorItem> errors) {}

