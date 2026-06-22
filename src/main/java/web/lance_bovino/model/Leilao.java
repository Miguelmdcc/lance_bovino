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
@Table(name = "leilao")
@DynamicUpdate
public class Leilao implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="gerador_leilao", sequenceName="leilao_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="gerador_leilao", strategy=GenerationType.SEQUENCE)
    private Long codigo;

    private String nome;

    @Column(name = "initial_price")
    private BigDecimal initialPrice;

    @Column(name = "final_timestamp")
    private LocalDateTime finalTimestamp;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_usuario", referencedColumnName = "codigo")
    private Usuario usuario;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_gado", referencedColumnName = "codigo")
    private Gado gado;

    @Enumerated(EnumType.STRING)
    private StatusLeilao status = StatusLeilao.AGUARDANDO;

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
        return "Leilao [codigo=" + codigo + ", nome=" + nome + ", initialPrice=" + initialPrice + ", finalTimestamp="
                + finalTimestamp + ", usuario=" + usuario + ", gado=" + gado + ", status=" + status + "]";
    }

}