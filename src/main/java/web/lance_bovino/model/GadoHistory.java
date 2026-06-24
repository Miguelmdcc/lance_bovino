package web.lance_bovino.model;

import java.io.Serializable;
import java.time.LocalDateTime;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.*;

@Entity
@Table(name = "gado_history")
@DynamicUpdate
public class GadoHistory implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="gerador_gado_history", sequenceName="gado_history_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="gerador_gado_history", strategy=GenerationType.SEQUENCE)
    private Long codigo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_gado", referencedColumnName = "codigo")
    private Gado gado;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_usuario", referencedColumnName = "codigo")
    private Usuario usuario;

    @Column(name = "timestamp_de_criacao")
    private LocalDateTime timestampDeCriacao;

    private boolean ativo;

    public GadoHistory() {}

    public GadoHistory(Gado gado, Usuario usuario, LocalDateTime timestampDeCriacao) {
        this.gado = gado;
        this.usuario = usuario;
        this.timestampDeCriacao = timestampDeCriacao;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
        this.codigo = codigo;
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

    public LocalDateTime getTimestampDeCriacao() {
        return timestampDeCriacao;
    }

    public void setTimestampDeCriacao(LocalDateTime timestampDeCriacao) {
        this.timestampDeCriacao = timestampDeCriacao;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "GadoHistory [codigo=" + codigo + ", gado=" + gado + ", usuario=" + usuario + ", timestampDeCriacao="
                + timestampDeCriacao + ", ativo=" + ativo + "]";
    }

}