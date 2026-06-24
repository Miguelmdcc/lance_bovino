CREATE TABLE public.gado
( 
    codigo bigserial NOT NULL, 
    nome text, 
    peso numeric(10,2),
    raca text,
    altura numeric(10,2),
    idade integer,
    codigo_usuario bigint NOT NULL,
    status text DEFAULT 'ATIVO',
    PRIMARY KEY (codigo),
    CONSTRAINT fk_gado_usuario FOREIGN KEY (codigo_usuario) REFERENCES public.usuario(codigo)
);

INSERT INTO gado (nome, peso, raca, altura, idade, codigo_usuario) VALUES
('gado1', 100.0, 'zebu', 100.0, 10, 1),
('gado2', 100.0, 'nelore', 100.0, 10, 2),
('gado3', 100.0, 'zebu', 100.0, 10, 3);