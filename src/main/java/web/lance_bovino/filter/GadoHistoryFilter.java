package web.lance_bovino.filter;

import java.time.LocalDateTime;

import web.lance_bovino.model.Gado;
import web.lance_bovino.model.Usuario;

public class GadoHistoryFilter {

    private Long codigo;
    private Gado gado;
    private Usuario usuario;
    private LocalDateTime timestampDeCriacao;
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
    @Override
    public String toString() {
        return "GadoHistoryFilter [codigo=" + codigo + ", gado=" + gado + ", usuario=" + usuario
                + ", timestampDeCriacao=" + timestampDeCriacao + "]";
    }
    
}
