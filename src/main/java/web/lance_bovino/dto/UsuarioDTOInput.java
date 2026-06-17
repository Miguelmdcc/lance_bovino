package web.lance_bovino.dto;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.validator.constraints.br.CPF;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import web.lance_bovino.model.BankMethod;
import web.lance_bovino.model.Papel;
import web.lance_bovino.model.Usuario;
import web.lance_bovino.validation.UniqueValueAttribute;
import web.lance_bovino.validation.cpfunico.CPFUnicoService;

@UniqueValueAttribute(attribute = "cpf", message = "Esse CPF já foi usado por outra pessoa", service = CPFUnicoService.class)
public class UsuarioDTOInput {

    private Long codigo;
    @NotBlank(message = "O nome da pessoa é obrigatório")
    @Size(max = 255, message = "O tamanho máximo do nome é 255 caracteres")
    private String nome;
    @NotBlank(message = "O CPF da pessoa é obrigatório")
    @CPF(message = "O formato do CPF é inválido")
    private String cpf;
    @NotNull(message = "O metodo de pagamento é obrigatório")
    private BankMethod metodoBancario;
    @NotBlank(message = "Os dados bancários são obrigatórios")
    private String dadosBancarios;
    @NotBlank(message = "A senha é obrigatória")
    @Size(min = 5, max = 255, message = "A senha deve ter entre 5 e 255 caracteres")
    private String senha;
    private boolean ativo = true;
    @Size(min = 1, message = "Ao menos um papel é obrigatório")
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

    public List<Papel> getPapeis() {
        return papeis;
    }

    public void setPapeis(List<Papel> papeis) {
        this.papeis = papeis;
    }

    public Usuario toUsuario() {
        Usuario user = new Usuario();
        user.setCodigo(codigo);
        user.setNome(nome);
        user.setCpf(cpf);
        user.setMetodoBancario(metodoBancario);
        user.setDadosBancarios(dadosBancarios);
        user.setAtivo(ativo);
        user.setPapeis(papeis);
        return user;
    }

    public static UsuarioDTOInput fromUsuario(Usuario usuario) {
        UsuarioDTOInput dto = new UsuarioDTOInput();
        dto.setCodigo(usuario.getCodigo());
        dto.setNome(usuario.getNome());
        dto.setCpf(usuario.getCpf());
        dto.setMetodoBancario(usuario.getMetodoBancario());
        dto.setDadosBancarios(usuario.getDadosBancarios());
        dto.setAtivo(usuario.isAtivo());
        dto.setPapeis(usuario.getPapeis());
        return dto;
    }

    @Override
    public String toString() {
        return "UsuarioDTOInput [codigo=" + codigo + ", nome=" + nome + ", cpf=" + cpf + ", metodoBancario="
                + metodoBancario + ", dadosBancarios=" + dadosBancarios + ", ativo=" + ativo + "]";
    }

}
