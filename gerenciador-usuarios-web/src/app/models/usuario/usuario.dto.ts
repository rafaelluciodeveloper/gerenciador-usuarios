export interface UsuarioDTO {
  id: string;
  nome: string;
  perfis: string[];
}

export interface CriarUsuarioDTO {
  nome: string;
  perfis: string[];
}

export interface AtualizarUsuarioDTO {
  nome: string;
  perfis: string[];
}
