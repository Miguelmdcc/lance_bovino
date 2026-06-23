package web.lance_bovino.filter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import web.lance_bovino.model.Gado;
import web.lance_bovino.model.StatusLeilao;
import web.lance_bovino.model.Usuario;

public class LeilaoFilter {

    private Long codigo;
    private String nome;
    private BigDecimal initialPrice;
    private LocalDateTime finalTimestamp;
    private Usuario usuario;
    private Gado gado;
    private StatusLeilao status;
    
    public Long getCodigo() {
        return codigo;
    }
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public BigDecimal getInitialPrice() {
        return initialPrice;
    }
    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }
    public LocalDateTime getFinalTimestamp() {
        return finalTimestamp;
    }
    public void setFinalTimestamp(LocalDateTime finalTimestamp) {
        this.finalTimestamp = finalTimestamp;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public Gado getGado() {
        return gado;
    }
    public void setGado(Gado gado) {
        this.gado = gado;
    }
    public StatusLeilao getStatus() {
        return status;
    }
    public void setStatus(StatusLeilao status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LeilaoFilter [codigo=" + codigo + ", nome=" + nome + ", initialPrice=" + initialPrice
                + ", finalTimestamp=" + finalTimestamp + ", usuario=" + usuario + ", gado=" + gado + ", status="
                + status + "]";
    }
    
}
