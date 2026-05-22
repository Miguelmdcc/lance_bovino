package com.example.filter;

public class GadoFilter {

    private Long codigo;
    private String nome;
    private Double peso;
    private String raca;
    private Double altura;
    private Integer idade;
    
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

    @Override
    public String toString() {
        return "GadoFilter [codigo=" + codigo + ", nome=" + nome + ", peso=" + peso + ", raca="
                + raca + ", altura=" + altura + ", idade=" + idade + "]";
    }
    
}
