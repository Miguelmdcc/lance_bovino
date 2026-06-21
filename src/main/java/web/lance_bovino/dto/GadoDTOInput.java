package web.lance_bovino.dto;

import org.springframework.format.annotation.NumberFormat;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import web.lance_bovino.model.Gado;
import web.lance_bovino.model.Status;
import web.lance_bovino.model.Usuario;

public class GadoDTOInput {

    private Long codigo;
    @NotBlank(message = "O nome do gado é obrigatório")
    @Size(max = 255, message = "O tamanho máximo do nome é 255 caracteres")
    private String nome;
    @NotNull(message = "O peso do gado é obrigatório")
    @Min(value = 1, message = "O peso do gado deve ser maior que 0")
    @NumberFormat(pattern = "#,##0.00")
    private Double peso;
    @NotBlank(message = "A raça do gado é obrigatória")
    private String raca;
    @NotNull(message = "A altura do gado é obrigatória")
    @Min(value = 1, message = "A altura do gado deve ser maior que 0")
    @NumberFormat(pattern = "#,##0.00")
    private Double altura;
    @NotNull(message = "A idade do gado é obrigatória")
    @Min(value = 1, message = "A idade do gado deve ser maior que 0")
    private Integer idade;
    private Long codigoUsuario;
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

    public Long getCodigoUsuario() {
        return codigoUsuario;
    }

    public void setCodigoUsuario(Long codigoUsuario) {
        this.codigoUsuario = codigoUsuario;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Gado toGado() {
        Gado gado = new Gado();
        gado.setCodigo(codigo);
        gado.setNome(nome);
        gado.setPeso(peso);
        gado.setRaca(raca);
        gado.setAltura(altura);
        gado.setIdade(idade);
        Usuario usuario = new Usuario();
        usuario.setCodigo(codigoUsuario);
        gado.setUsuario(usuario);
        gado.setStatus(status);
        return gado;
    }

    public static GadoDTOInput fromGado(Gado gado) {
        GadoDTOInput dto = new GadoDTOInput();
        dto.setCodigo(gado.getCodigo());
        dto.setNome(gado.getNome());
        dto.setPeso(gado.getPeso());
        dto.setRaca(gado.getRaca());
        dto.setAltura(gado.getAltura());
        dto.setIdade(gado.getIdade());
        dto.setCodigoUsuario(gado.getUsuario().getCodigo());
        dto.setStatus(gado.getStatus());
        return dto;
    }

    @Override
    public String toString() {
        return "GadoDTOInput [codigo=" + codigo + ", nome=" + nome + ", peso=" + peso + ", raca=" + raca + ", altura="
                + altura + ", idade=" + idade + ", codigoUsuario=" + codigoUsuario + ", status=" + status + "]";
    }

}
