import { AbstractControl, ValidationErrors } from '@angular/forms';
import { Perfil } from './perfil/perfil.model';
import { Usuario } from './usuario/usuario.model';
import { Validators } from '@angular/forms';

export class ModelValidators {
    static validarPerfil(perfil: Perfil): string[] {
        const erros: string[] = [];

        if (!perfil.descricao || perfil.descricao.length < 5) {
            erros.push('A descrição do perfil deve ter no mínimo 5 caracteres');
        }

        return erros;
    }

    static validarUsuario(usuario: Usuario): string[] {
        const erros: string[] = [];

        if (!usuario.nome || usuario.nome.length < 10) {
            erros.push('O nome do usuário deve ter no mínimo 10 caracteres');
        }

        if (!usuario.perfis || usuario.perfis.length === 0) {
            erros.push('O usuário deve ter pelo menos um perfil');
        }

        return erros;
    }

    static usuarioFormValidator() {
        return {
            nome: ['', [Validators.required, Validators.minLength(10)]],
            perfis: ['', [Validators.required, (control: AbstractControl): ValidationErrors | null => {
                const perfis = control.value as Perfil[];
                if (!perfis || perfis.length === 0) {
                    return { required: true };
                }
                return null;
            }]]
        };
    }
}
