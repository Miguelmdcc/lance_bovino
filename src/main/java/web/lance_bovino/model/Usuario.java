package web.lance_bovino.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "usuario")
@DynamicUpdate
public class Usuario implements Serializable {

	private static final long serialVersionUID = 5757384541654785800L; // Gere outro valor

	@Id
	@SequenceGenerator(name="gerador55", sequenceName="usuario_codigo_seq", allocationSize=1)
	@GeneratedValue(generator="gerador55", strategy=GenerationType.SEQUENCE)
	private Long codigo;

	private String nome;
	private String cpf;
	@Column(name = "metodo_bancario")
	@Enumerated(EnumType.STRING)
	private BankMethod metodoBancario;
	@Column(name = "dados_bancarios")
	private String dadosBancarios;
	private String senha;
	private boolean ativo;
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "usuario_papel", joinColumns = @JoinColumn(name = "codigo_usuario"), inverseJoinColumns = @JoinColumn(name = "codigo_papel"))
	private List<Papel> papeis = new ArrayList<>();
	
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
		return metodoBancario;
	}
	public void setMetodoBancario(BankMethod metodoBancario) {
		this.metodoBancario = metodoBancario;
	}
	public String getDadosBancarios() {
		return dadosBancarios;
	}
	public void setDadosBancarios(String dadosBancarios) {
		this.dadosBancarios = dadosBancarios;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public boolean isAtivo() {
		return ativo;
	}
	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}
		public void adicionarPapel(Papel papel) {
		papeis.add(papel);
	}

	public void removerPapel(Papel papel) {
		papeis.remove(papel);
	}

	public List<Papel> getPapeis() {
		return papeis;
	}

	public void setPapeis(List<Papel> papeis) {
		this.papeis = papeis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		result = prime * result + ((cpf == null) ? 0 : cpf.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		if (cpf == null) {
			if (other.cpf != null)
				return false;
		} else if (!cpf.equals(other.cpf))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Usuario [codigo=" + codigo + ", nome=" + nome + ", cpf=" + cpf + ", metodoBancario=" + metodoBancario
				+ ", dadosBancarios=" + dadosBancarios + ", senha=" + senha + ", ativo=" + ativo + "]";
	}
}
