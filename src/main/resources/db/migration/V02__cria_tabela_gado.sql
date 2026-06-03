CREATE TABLE public.gado
( 
    codigo bigserial NOT NULL, 
    nome text, 
    peso numeric(10,2),
    raca text,
    altura numeric(10,2),
    idade integer,
    owner integer REFERENCES usuario(codigo),
    status text DEFAULT 'ATIVO',
    PRIMARY KEY (codigo) 
);