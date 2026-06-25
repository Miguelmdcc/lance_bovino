package web.lance_bovino.filter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import web.lance_bovino.model.Leilao;
import web.lance_bovino.model.Usuario;

public class LeilaoBidHistoryFilter {

    private Long codigo;    
    private Leilao leilao;
    private Usuario usuario;
    private BigDecimal bidValue;
    private LocalDateTime timestampDeCriacao;
    public Long getCodigo() {
        return codigo;
    }
    public void setCodigo(Long codigo) {
        this.codigo = codigo;
    }
    public Leilao getLeilao() {
        return leilao;
    }
    public void setLeilao(Leilao leilao) {
        this.leilao = leilao;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
    public BigDecimal getBidValue() {
        return bidValue;
    }
    public void setBidValue(BigDecimal bidValue) {
        this.bidValue = bidValue;
    }
    public LocalDateTime getTimestampDeCriacao() {
        return timestampDeCriacao;
    }
    public void setTimestampDeCriacao(LocalDateTime timestampDeCriacao) {
        this.timestampDeCriacao = timestampDeCriacao;
    }
    @Override
    public String toString() {
        return "LeilaoBidHistoryFilter [codigo=" + codigo + ", leilao=" + leilao + ", usuario=" + usuario
                + ", bidValue=" + bidValue + ", timestampDeCriacao=" + timestampDeCriacao + "]";
    }

    
    
}
