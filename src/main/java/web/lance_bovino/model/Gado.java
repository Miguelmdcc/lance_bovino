package web.lance_bovino.model;

import java.io.Serializable;

import org.hibernate.annotations.DynamicUpdate;

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
@Table(name = "gado")
@DynamicUpdate
public class Gado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="gerador_gado", sequenceName="gado_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="gerador_gado", strategy=GenerationType.SEQUENCE)
    private Long codigo;

    private String nome;
    
    private Double peso;
    
    private String raca;
    
    private Double altura;
    
    private Integer idade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "codigo_usuario", referencedColumnName = "codigo")
    private Usuario usuario;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

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

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    public Double getAltura() {
        return altura;
    }

    public void setAltura(Double altura) {
        this.altura = altura;
    }

    public Integer getIdade() {
        return idade;
    }

    public void setIdade(Integer idade) {
        this.idade = idade;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Gado [codigo=" + codigo + ", nome=" + nome + ", peso=" + peso + ", raca=" + raca + ", altura=" + altura
                + ", idade=" + idade + ", usuario=" + usuario + ", status=" + status + "]";
    }

}