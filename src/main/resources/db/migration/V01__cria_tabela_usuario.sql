BEGIN;

CREATE TABLE public.usuario
(
    codigo bigserial NOT NULL,
    nome text,
    cpf text,
    metodo_bancario text,
    dados_bancarios text,
    senha text,
    status text DEFAULT 'ATIVO',
    PRIMARY KEY (codigo)
);

INSERT INTO usuario (nome, cpf, metodo_bancario, dados_bancarios, senha) VALUES
('Teste ADM', 123654789, 'PIX','450cvv','{noop}12345');

END;