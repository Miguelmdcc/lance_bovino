package com.example.model;

import java.io.Serializable;
import org.hibernate.annotations.DynamicUpdate;
import jakarta.persistence.*;

@Entity
@Table(name = "gado")
@DynamicUpdate
public class Gado implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @SequenceGenerator(name="gerador_gado", sequenceName="gado_codigo_seq", allocationSize=1)
    @GeneratedValue(generator="gerador_gado", strategy=GenerationType.SEQUENCE)
    private Long id;

    private String nome;
    
    private Double peso;
    
    private String raca;
    
    private Double altura;
    
    private Integer idade;

    @Enumerated(EnumType.STRING)
    private Status status = Status.ATIVO;

    // Construtor padrão necessário para o JPA
    public Gado() {
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    // Método toString para ajudar nos logs do sistema
    @Override
    public String toString() {
        return "Gado [id=" + id + ", nome=" + nome + ", peso=" + peso + ", raca=" + raca + 
               ", altura=" + altura + ", idade=" + idade + ", status=" + status + "]";
    }
}