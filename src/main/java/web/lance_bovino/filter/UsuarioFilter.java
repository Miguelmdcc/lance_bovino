package web.lance_bovino.filter;

import web.lance_bovino.model.BankMethod;

public class UsuarioFilter {

	private Long codigo;
	private String nome;
	private String cpf;
	private BankMethod metodoBancario;
	private String dadosBancarios;
    private boolean ativo;

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
    public boolean isAtivo() {
        return ativo;
    }
    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "UsuarioFilter [codigo=" + codigo + ", nome=" + nome + ", cpf=" + cpf
                + ", metodoBancario=" + metodoBancario + ", dadosBancarios=" + dadosBancarios
                + ", ativo=" + ativo + "]";
    }

}
