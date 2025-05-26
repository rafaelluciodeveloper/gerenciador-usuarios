import { Perfil } from './perfil.model';

describe('Perfil', () => {
  it('deve criar uma instância vazia', () => {
    const perfil = new Perfil();
    expect(perfil).toBeTruthy();
    expect(perfil.id).toBe('');
    expect(perfil.descricao).toBe('');
  });

  it('deve criar uma instância com dados', () => {
    const data = {
      id: '1',
      descricao: 'Perfil Teste'
    };
    const perfil = new Perfil(data);
    expect(perfil.id).toBe(data.id);
    expect(perfil.descricao).toBe(data.descricao);
  });

  it('deve criar uma instância com dados parciais', () => {
    const data = {
      descricao: 'Perfil Teste'
    };
    const perfil = new Perfil(data);
    expect(perfil.id).toBe('');
    expect(perfil.descricao).toBe(data.descricao);
  });
});
