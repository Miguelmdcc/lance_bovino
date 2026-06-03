package web.lance_bovino.filter;

import web.lance_bovino.model.BankMethod;

public class UsuarioFilter {

	private Long codigo;
	private String nome;
	private String cpf;
	private BankMethod metodo_bancario;
	private Long dadosBancarios;

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
    public BankMethod getMetodo_bancario() {
        return metodo_bancario;
    }
    public void setMetodo_bancario(BankMethod metodo_bancario) {
        this.metodo_bancario = metodo_bancario;
    }
    public Long getDadosBancarios() {
        return dadosBancarios;
    }
    public void setDadosBancarios(Long dadosBancarios) {
        this.dadosBancarios = dadosBancarios;
    }

    @Override
    public String toString() {
        return "UsuarioFilter [codigo=" + codigo + ", nome=" + nome + ", cpf=" + cpf
                + ", metodo_bancario=" + metodo_bancario + ", dadosBancarios=" + dadosBancarios
                + "]";
    }

}
