CREATE TABLE public.gado_history
( 
    codigo bigserial NOT NULL, 
    codigo_gado bigserial NOT NULL,
    codigo_usuario bigserial NOT NULL,
    timestamp_de_criacao timestamp NOT NULL, 
    PRIMARY KEY (codigo),
    CONSTRAINT fk_usuario FOREIGN KEY (codigo_usuario) REFERENCES public.usuario(codigo),
    CONSTRAINT fk_gado FOREIGN KEY (codigo_gado) REFERENCES public.gado(codigo)
);

INSERT INTO gado_history (codigo_gado,codigo_usuario,timestamp_de_criacao) VALUES
(1, 1, '2026-06-21 02:25:00'),
(1, 2, '2026-06-22 02:25:00'),
(1, 3, '2026-06-23 02:25:00'),
(2, 2, '2026-06-21 02:25:00'),
(2, 3, '2026-06-22 02:25:00'),
(2, 1, '2026-06-23 02:25:00'),
(3, 3, '2026-06-21 02:25:00'),
(3, 1, '2026-06-22 02:25:00'),
(3, 2, '2026-06-23 02:25:00');
