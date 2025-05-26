ALTER TABLE usuario_perfil
DROP CONSTRAINT fk_perfil;

ALTER TABLE usuario_perfil
    ADD CONSTRAINT fk_perfil
        FOREIGN KEY (perfil_id) REFERENCES perfis(id)
            ON DELETE RESTRICT;
