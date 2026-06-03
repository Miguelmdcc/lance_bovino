package web.lance_bovino.model;

import java.io.Serializable;
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
@Table(name = "usuario")
@DynamicUpdate
public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;
	
	@Id
	@SequenceGenerator(name="gerador_usuario", sequenceName="user_codigo_seq", allocationSize=1)
	@GeneratedValue(generator="gerador_usuario", strategy=GenerationType.SEQUENCE)
	private Long codigo;

	private String nome;
	private String cpf;
	@Column(name = "metodo_bancario")
	@Enumerated(EnumType.STRING)
	private BankMethod metodo_bancario;
	@Column(name = "dados_bancarios")
	private String dadosBancarios;
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
	public BankMethod getMetodoBancario() {
		return metodo_bancario;
	}
	public void setMetodoBancario(BankMethod metodo_bancario) {
		this.metodo_bancario = metodo_bancario;
	}
	public String getDadosBancarios() {
		return dadosBancarios;
	}
	public void setDadosBancarios(String dadosBancarios) {
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
		return "User [codigo=" + codigo + ", nome=" + nome + ", cpf=" + cpf + ", metodo_bancario=" + metodo_bancario
				+ ", dadosBancarios=" + dadosBancarios + ", status=" + status + "]";
	}
	
}
