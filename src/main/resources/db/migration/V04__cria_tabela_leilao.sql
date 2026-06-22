CREATE TABLE public.leilao
( 
    codigo bigserial NOT NULL, 
    nome text, 
    initial_price numeric(10,2),
    final_timestamp timestamp,
    codigo_usuario bigint NOT NULL,
    codigo_gado bigint NOT NULL,
    status text DEFAULT 'Aguardando Início',
    PRIMARY KEY (codigo),
    CONSTRAINT fk_leilao_usuario FOREIGN KEY (codigo_usuario) REFERENCES public.usuario(codigo),
    CONSTRAINT fk_leilao_gado FOREIGN KEY (codigo_gado) REFERENCES public.gado(codigo)
);