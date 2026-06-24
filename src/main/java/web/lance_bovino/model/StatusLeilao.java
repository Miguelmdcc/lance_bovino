package web.lance_bovino.model;

public enum StatusLeilao {
    AGUARDANDO("Aguardando Início", Status.ATIVO),
    ABERTO("Aberto para Lances", Status.ATIVO),
    ENCERRADO("Encerrado", Status.INATIVO);

    private final String descricao;
    private final Status statusDoSistema;

    private StatusLeilao(String descricao, Status statusDoSistema) {
        this.descricao = descricao;
        this.statusDoSistema = statusDoSistema;
    }

    public String getDescricao() {
        return descricao;
    }

    public Status getStatusDoSistema() {
        return statusDoSistema;
    }
}