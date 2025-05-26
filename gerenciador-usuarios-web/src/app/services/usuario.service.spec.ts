import { TestBed } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { UsuarioService } from './usuario.service';

describe('UsuarioService', () => {
  let service: UsuarioService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        UsuarioService,
        { provide: HttpClient, useValue: {} }
      ]
    });
    service = TestBed.inject(UsuarioService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
