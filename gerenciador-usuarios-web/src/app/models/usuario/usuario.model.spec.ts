import { Usuario } from './usuario.model';
import { Perfil } from '../perfil/perfil.model';

describe('Usuario', () => {
  it('deve criar uma instância vazia', () => {
    const usuario = new Usuario();
    expect(usuario).toBeTruthy();
    expect(usuario.id).toBe('');
    expect(usuario.nome).toBe('');
    expect(usuario.perfis).toEqual([]);
  });

  it('deve criar uma instância com dados', () => {
    const perfis = [
      new Perfil({ id: '1', descricao: 'Perfil 1' }),
      new Perfil({ id: '2', descricao: 'Perfil 2' })
    ];
    const data = {
      id: '1',
      nome: 'Usuário Teste',
      perfis
    };
    const usuario = new Usuario(data);
    expect(usuario.id).toBe(data.id);
    expect(usuario.nome).toBe(data.nome);
    expect(usuario.perfis).toEqual(data.perfis);
  });

  it('deve criar uma instância com dados parciais', () => {
    const data = {
      nome: 'Usuário Teste'
    };
    const usuario = new Usuario(data);
    expect(usuario.id).toBe('');
    expect(usuario.nome).toBe(data.nome);
    expect(usuario.perfis).toEqual([]);
  });
});
