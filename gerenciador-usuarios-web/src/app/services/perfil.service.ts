import { Injectable } from '@angular/core';
import { HttpClient, HttpParams, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';

interface PerfilDTO {
  id: string;
  descricao: string;
}

interface CriarPerfilDTO {
  descricao: string;
}

interface AtualizarPerfilDTO {
  descricao: string;
}

interface PageResponsePerfilDTO {
  content: PerfilDTO[];
  totalPages: number;
  totalElements: number;
}

interface PerfilFiltro {
  page?: number;
  size?: number;
  descricao?: string;
  sort?: string[];
}

@Injectable({
  providedIn: 'root'
})
export class PerfilService {
  private apiUrl = `${environment.apiUrl}/perfis`;

  constructor(private http: HttpClient) {}

  criar(perfil: CriarPerfilDTO): Observable<HttpResponse<PerfilDTO>> {
    return this.http.post<PerfilDTO>(this.apiUrl, perfil, { observe: 'response' });
  }

  atualizar(id: string, perfil: AtualizarPerfilDTO): Observable<HttpResponse<PerfilDTO>> {
    return this.http.patch<PerfilDTO>(`${this.apiUrl}/${id}`, perfil, { observe: 'response' });
  }

  detalhar(id: string): Observable<PerfilDTO> {
    return this.http.get<PerfilDTO>(`${this.apiUrl}/${id}`);
  }

  excluir(id: string): Observable<HttpResponse<void>> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { observe: 'response' });
  }

  listar(filtro: PerfilFiltro = { page: 0, size: 10, sort: ['descricao,asc'] }): Observable<PageResponsePerfilDTO> {
    let params = new HttpParams()
      .set('page', filtro.page?.toString() || '0')
      .set('size', filtro.size?.toString() || '10');

    if (filtro.descricao) {
      params = params.set('descricao', filtro.descricao);
    }

    const sortArray = Array.isArray(filtro.sort) ? filtro.sort : [filtro.sort];
    sortArray.forEach(sort => {
      if (sort) {
        params = params.append('sort', sort);
      }
    });

    return this.http.get<PageResponsePerfilDTO>(this.apiUrl, { params });
  }
}
