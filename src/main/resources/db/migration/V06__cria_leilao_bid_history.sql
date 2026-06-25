CREATE TABLE public.leilao_bid_history
( 
    codigo bigserial NOT NULL, 
    codigo_leilao bigserial NOT NULL,
    codigo_usuario bigserial NOT NULL,
    bid_value numeric(10,2),
    timestamp_de_criacao timestamp NOT NULL, 
    PRIMARY KEY (codigo),
    CONSTRAINT fk_leilao FOREIGN KEY (codigo_leilao) REFERENCES public.leilao(codigo),
    CONSTRAINT fk_usuario FOREIGN KEY (codigo_usuario) REFERENCES public.usuario(codigo)
);
