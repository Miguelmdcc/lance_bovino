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