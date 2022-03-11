ALTER TABLE tb_usuario ADD COLUMN ativo VARCHAR(1);

UPDATE tb_usuario SET ativo = 'S';

ALTER TABLE tb_usuario ALTER COLUMN ativo SET NOT NULL;