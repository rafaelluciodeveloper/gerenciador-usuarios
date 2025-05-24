CREATE TABLE usuario_perfil
(
    usuario_id UUID NOT NULL,
    perfil_id  UUID NOT NULL,
    PRIMARY KEY (usuario_id, perfil_id),
    CONSTRAINT fk_usuario FOREIGN KEY (usuario_id)
        REFERENCES usuarios (id)
        ON DELETE CASCADE,
    CONSTRAINT fk_perfil FOREIGN KEY (perfil_id)
        REFERENCES perfis (id)
        ON DELETE CASCADE
);