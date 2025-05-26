export class Perfil {
    id = '';
    descricao = '';

    constructor(data?: Partial<Perfil>) {
        if (data) {
            Object.assign(this, data);
        }
    }
}
