import { Perfil } from '../perfil/perfil.model';

export class Usuario {
    id = '';
    nome = '';
    perfis: Perfil[] = [];

    constructor(data?: Partial<Usuario>) {
        if (data) {
            Object.assign(this, data);
            this.perfis = (data.perfis || []).map(p => new Perfil(p));
        }
    }
}
