import { TestBed } from '@angular/core/testing';
import { HttpClient } from '@angular/common/http';
import { PerfilService } from './perfil.service';

describe('PerfilService', () => {
  let service: PerfilService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        PerfilService,
        { provide: HttpClient, useValue: {} }
      ]
    });
    service = TestBed.inject(PerfilService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
