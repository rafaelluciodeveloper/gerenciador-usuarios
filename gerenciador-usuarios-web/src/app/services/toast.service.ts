import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';

interface ApiError {
  timestamp: string;
  status: number;
  message: string;
  path: string;
}

interface FieldErrorItem {
  field: string;
  message: string;
}

interface ValidationErrorResponse {
  errors: FieldErrorItem[];
}

@Injectable({
  providedIn: 'root'
})
export class ToastService {
  constructor(private snackBar: MatSnackBar) {}

  success(message: string) {
    this.snackBar.open(message, 'Fechar', {
      duration: 3000,
      horizontalPosition: 'end',
      verticalPosition: 'top',
      panelClass: ['success-snackbar']
    });
  }

  handleResponse(response: HttpResponse<unknown> | null, operation: string) {
    if (response) {
      if (response.status === 201) {
        this.success(`${operation} criado com sucesso`);
        return true;
      }
      if (response.status === 200) {
        this.success(`${operation} atualizado com sucesso`);
        return true;
      }
      if (response.status === 204) {
        this.success(`${operation} excluído com sucesso`);
        return true;
      }
    }
    return false;
  }

  error(error: HttpErrorResponse) {
    let message: string;

    if (error.error) {
      if (this.isValidationError(error.error)) {
        message = error.error.errors.map(err => err.message).join('\n');
      }
      else if (this.isApiError(error.error)) {
        message = error.error.message;
      }
      else {
        message = 'Ocorreu um erro inesperado';
      }
    } else {
      message = 'Ocorreu um erro inesperado';
    }

    this.snackBar.open(message, 'Fechar', {
      duration: 5000,
      horizontalPosition: 'end',
      verticalPosition: 'top',
      panelClass: ['error-snackbar']
    });
  }

  private isValidationError(error: unknown): error is ValidationErrorResponse {
    return Boolean(
      error &&
      typeof error === 'object' &&
      'errors' in error &&
      Array.isArray((error as { errors: unknown[] }).errors) &&
      (error as { errors: unknown[] }).errors.length > 0
    );
  }

  private isApiError(error: unknown): error is ApiError {
    return Boolean(
      error &&
      typeof error === 'object' &&
      'timestamp' in error &&
      'status' in error &&
      'message' in error &&
      'path' in error
    );
  }
}
