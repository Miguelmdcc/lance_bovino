package com.example.model;

import java.io.Serializable;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "user")
@DynamicUpdate
public class User implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="gerador2", sequenceName="user_codigo_seq", allocationSize=1)
	@GeneratedValue(generator="gerador2", strategy=GenerationType.SEQUENCE)
	private Long codigo;
	private String nome;
	private String cpf;
	@Column(name = "metodo_bancario")
	@Enumerated(EnumType.STRING)
	private BankMethod metodo_bancario;
	@Column(name = "dados_bancarios")
	private Long dadosBancarios;
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
	public String getCpf() {
		return cpf;
	}
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	public Long getDadosBancarios() {
		return dadosBancarios;
	}
	public void setDadosBancarios(Long dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "User [codigo=" + codigo + ", nome=" + nome + ", cpf=" + cpf + ", dadosBancarios="
				+ dadosBancarios + ", status=" + status + "]";
	}
}
