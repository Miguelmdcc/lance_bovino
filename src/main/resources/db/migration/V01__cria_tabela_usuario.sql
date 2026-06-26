BEGIN;

CREATE TABLE public.usuario
(
    codigo bigserial NOT NULL,
    nome text,
    cpf text,
    metodo_bancario text,
    dados_bancarios text,
    senha text,
    ativo boolean DEFAULT true,
    PRIMARY KEY (codigo)
);

INSERT INTO usuario (nome, cpf, metodo_bancario, dados_bancarios, senha) VALUES
('Teste ADM', '752.217.790-13', 'PIX', '450cvv', '{noop}12345'),
('Teste USER', '383.868.940-24', 'PIX', '450cvv', '{noop}12345'),
('Teste USEReADM', '259.122.000-00', 'PIX', '450cvv', '{noop}12345');

END;