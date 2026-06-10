BEGIN;

CREATE TABLE public.papel
(
    codigo bigserial NOT NULL,
    nome text,
    PRIMARY KEY (codigo)
);

CREATE TABLE public.usuario_papel
(
    codigo_usuario bigint NOT NULL,
    codigo_papel bigint NOT NULL
);

ALTER TABLE public.usuario_papel
    ADD FOREIGN KEY (codigo_usuario)
    REFERENCES public.usuario (codigo)
    NOT VALID;


ALTER TABLE public.usuario_papel
    ADD FOREIGN KEY (codigo_papel)
    REFERENCES public.papel (codigo)
    NOT VALID;

INSERT INTO public.usuario (nome, cpf, metodo_bancario, dados_bancarios, senha) VALUES
('Teste USER', 123654789, 'PIX','450cvv','{noop}12345'),
('Teste USEReADM', 123654789, 'PIX','450cvv','{noop}12345');

INSERT INTO public.papel (codigo, nome) VALUES 
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USUARIO');

INSERT INTO public.usuario_papel (codigo_usuario, codigo_papel) VALUES
(1, 1),
(2, 2),
(3, 1),
(3, 2);

END;