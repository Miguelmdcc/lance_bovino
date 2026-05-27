package web.lance_bovino.dto;

import org.hibernate.validator.constraints.br.CPF;
import web.lance_bovino.model.BankMethod;
import web.lance_bovino.model.Status;
import web.lance_bovino.model.User;
import web.lance_bovino.validation.UniqueValueAttribute;
import web.lance_bovino.validation.cpfunico.CPFUnicoService;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@UniqueValueAttribute(attribute = "cpf", message = "Esse CPF já foi usado por outra pessoa", service = CPFUnicoService.class)
public class UsuarioDTOInput {

    private Long codigo;
    @NotBlank(message = "O nome da pessoa é obrigatório")
    @Size(max = 255, message = "O tamanho máximo do nome é 255 caracteres")
    private String nome;
    @NotBlank(message = "O CPF da pessoa é obrigatório")
    @CPF(message = "O formato do CPF é inválido")
    private String cpf;
    @NotBlank(message = "O metodo de pagamento é obrigatório")
    private BankMethod metodoBancario;
    @NotBlank(message = "Os dados bancários são obrigatórios")
    private String dadosBancarios;
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

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public User toUser() {
        User user = new User();
        user.setCodigo(codigo);
        user.setNome(nome);
        user.setCpf(cpf);
        user.setMetodoBancario(metodoBancario);
        user.setDadosBancarios(dadosBancarios);
        user.setStatus(status);
        return user;
    }

    public static UsuarioDTOInput fromPessoa(User usuario) {
        UsuarioDTOInput dto = new UsuarioDTOInput();
        dto.setCodigo(usuario.getCodigo());
        dto.setNome(usuario.getNome());
        dto.setCpf(usuario.getCpf());
        dto.setMetodoBancario(usuario.getMetodoBancario());
        dto.setDadosBancarios(usuario.getDadosBancarios());
        dto.setStatus(usuario.getStatus());
        return dto;
    }

    @Override
    public String toString() {
        return "UsuarioDTOInput [codigo=" + codigo + ", nome=" + nome + ", cpf=" + cpf + ", metodoBancario="
                + metodoBancario + ", dadosBancarios=" + dadosBancarios + ", status=" + status + "]";
    }

}
