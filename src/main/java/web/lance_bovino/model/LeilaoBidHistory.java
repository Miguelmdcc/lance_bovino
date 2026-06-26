package web.lance_bovino.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "leilao_bid_history")
@DynamicUpdate
public class LeilaoBidHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="gerador_leilao", sequenceName="leilao_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="gerador_leilao", strategy=GenerationType.SEQUENCE)
    private Long codigo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_leilao", referencedColumnName = "codigo")
    private Leilao leilao;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_usuario", referencedColumnName = "codigo")
    private Usuario usuario;

    @Column(name = "bid_value")
    private BigDecimal bidValue;

    @Column(name = "timestamp_de_criacao")
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
        return "LeilaoBidHistory [codigo=" + codigo + ", leilao=" + leilao + ", usuario=" + usuario + ", bidValue="
                + bidValue + ", timestampDeCriacao=" + timestampDeCriacao + "]";
    }

}