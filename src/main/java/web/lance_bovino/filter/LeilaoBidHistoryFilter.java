package web.lance_bovino.filter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import web.lance_bovino.model.Gado;
import web.lance_bovino.model.Leilao;
import web.lance_bovino.model.StatusLeilao;
import web.lance_bovino.model.Usuario;

public class LeilaoBidHistoryFilter {

    private Long codigo;
    private String nome;
    private Leilao leilao;
    private Gado gado;
    private Usuario usuario;
    private BigDecimal bidValue;
    private LocalDateTime timestampDeCriacao;
    private String vencedor;
    
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
    public Leilao getLeilao() {
        return leilao;
    }
    public void setLeilao(Leilao leilao) {
        this.leilao = leilao;
    }
    public Gado getGado() {
        return gado;
    }
    public void setGado(Gado gado) {
        this.gado = gado;
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
    public String getVencedor() {
        return vencedor;
    }
    public void setVencedor(String vencedor) {
        this.vencedor = vencedor;
    }
    public StatusLeilao getStatus() {
        return status;
    }
    public void setStatus(StatusLeilao status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LeilaoBidHistoryFilter [codigo=" + codigo + ", nome=" + nome + ", leilao=" + leilao + ", gado=" + gado
                + ", usuario=" + usuario + ", bidValue=" + bidValue + ", timestampDeCriacao=" + timestampDeCriacao
                + ", vencedor=" + vencedor + ", status=" + status + "]";
    }
}