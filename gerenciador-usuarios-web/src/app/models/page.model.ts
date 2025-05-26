export interface PageRequest {
  page: number;
  size: number;
  sort?: string[];
}

export interface PageResponse<T> {
  content: T[];
  totalPages: number;
  totalElements: number;
}

export interface UsuarioFiltro extends PageRequest {
  nome?: string;
}

export interface PerfilFiltro extends PageRequest {
  descricao?: string;
}
