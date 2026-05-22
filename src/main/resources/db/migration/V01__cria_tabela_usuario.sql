CREATE TABLE public.usuario
(
    codigo bigserial NOT NULL,
    nome text,
    cpf text,
    metodo_bancario text,
    dados_bancarios text,
    status text DEFAULT 'ATIVO',
    PRIMARY KEY (codigo)
);

INSERT INTO usuario (nome, cpf, metodo_bancario, dados_bancarios) VALUES
('Teste', 123654789, 'PIX','450cvv');