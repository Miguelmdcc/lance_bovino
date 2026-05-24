package com.example.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.DynamicUpdate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
    private Long id;

    private String nome;

    @Column(name = "initial_price")
    private BigDecimal initialPrice;

    @Column(name = "final_timestamp")
    private LocalDateTime finalTimestamp;

    @Enumerated(EnumType.STRING)
    private StatusLeilao status = StatusLeilao.AGUARDANDO;

    // Construtor padrão do JPA
    public Leilao() {
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

    public StatusLeilao getStatus() {
        return status;
    }

    public void setStatus(StatusLeilao status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Leilao [id=" + id + ", nome=" + nome + ", initialPrice=" + initialPrice + 
               ", finalTimestamp=" + finalTimestamp + ", status=" + status + "]";
    }
}