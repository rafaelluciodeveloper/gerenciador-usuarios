import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

interface PerfilDTO {
  id: string;
  descricao: string;
}

interface UsuarioDTO {
  id: string;
  nome: string;
  perfis: PerfilDTO[];
}

interface CriarUsuarioDTO {
  nome: string;
  perfis: string[];
}

interface AtualizarUsuarioDTO {
  nome: string;
  perfis: string[];
}

interface PageResponseUsuarioDTO {
  content: UsuarioDTO[];
  totalPages: number;
  totalElements: number;
}

interface UsuarioFiltro {
  page?: number;
  size?: number;
  nome?: string;
  sort?: string[];
}

@Injectable({
  providedIn: 'root'
})
export class UsuarioService {
  private apiUrl = `${environment.apiUrl}/usuarios`;

  constructor(private http: HttpClient) {}

  criar(usuario: CriarUsuarioDTO): Observable<HttpResponse<UsuarioDTO>> {
    return this.http.post<UsuarioDTO>(this.apiUrl, usuario, { observe: 'response' });
  }

  atualizar(id: string, usuario: AtualizarUsuarioDTO): Observable<HttpResponse<UsuarioDTO>> {
    return this.http.patch<UsuarioDTO>(`${this.apiUrl}/${id}`, usuario, { observe: 'response' });
  }

  detalhar(id: string): Observable<UsuarioDTO> {
    return this.http.get<UsuarioDTO>(`${this.apiUrl}/${id}`);
  }

  excluir(id: string): Observable<HttpResponse<void>> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { observe: 'response' });
  }

  listar(filtro: UsuarioFiltro = { page: 0, size: 10, sort: ['nome,asc'] }): Observable<PageResponseUsuarioDTO> {
    let params = new HttpParams()
      .set('page', filtro.page?.toString() || '0')
      .set('size', filtro.size?.toString() || '10');

    if (filtro.nome) {
      params = params.set('nome', filtro.nome);
    }

    const sortArray = Array.isArray(filtro.sort) ? filtro.sort : [filtro.sort];
    sortArray.forEach(sort => {
      if (sort) {
        params = params.append('sort', sort);
      }
    });

    return this.http.get<PageResponseUsuarioDTO>(this.apiUrl, { params });
  }
}
