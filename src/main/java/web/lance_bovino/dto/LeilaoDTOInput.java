package web.lance_bovino.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import web.lance_bovino.model.Gado;
import web.lance_bovino.model.Leilao;
import web.lance_bovino.model.StatusLeilao;
import web.lance_bovino.model.Usuario;

public class LeilaoDTOInput {

    private Long codigo;
    @NotBlank(message = "O nome do leilão é obrigatório")
    @Size(max = 255, message = "O tamanho máximo do nome é 255 caracteres")
    private String nome;
    @NotNull(message = "O preço inicial de lance é obrigatório")
    @Min(value = 1, message = "O lance inicial tem que ser pelo menos um real")
    private BigDecimal initialPrice;
    @NotNull(message = "Data final do lance é obrigatória")
    @Future(message = "Data final tem que ser no futuro")
    private LocalDateTime finalDateTime;
    @NotBlank(message = "A data final é obrigatória")
    private String dataFinal;
    @NotBlank(message = "A hora final é obrigatória")
    private String horaFinal;
    private Usuario usuario;
    @NotNull(message = "O gado é obrigatório")
    private Gado gado;
    private StatusLeilao status;
    private Usuario vencedor;
    
    private void atualizarLocalDateTime() {
        if (dataFinal != null && !dataFinal.isBlank() && horaFinal != null && !horaFinal.isBlank()) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate date = LocalDate.parse(this.dataFinal, formatter);
                LocalTime time = LocalTime.parse(this.horaFinal);
                this.finalDateTime = LocalDateTime.of(date, time);
            } catch (Exception e) {
                this.finalDateTime = null; 
            }
        }
    }

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

    public BigDecimal getInitialPrice() {
        return initialPrice;
    }

    public void setInitialPrice(BigDecimal initialPrice) {
        this.initialPrice = initialPrice;
    }

    public LocalDateTime getFinalDateTime() {
        return finalDateTime;
    }

    public void setFinalDateTime(LocalDateTime finalDateTime) {
        this.finalDateTime = finalDateTime;
    }

    public String getDataFinal() {
        return dataFinal;
    }

    public void setDataFinal(String dataFinal) {
        this.dataFinal = dataFinal;
        atualizarLocalDateTime();
    }

    public String getHoraFinal() {
        return horaFinal;
    }

    public void setHoraFinal(String horaFinal) {
        this.horaFinal = horaFinal;
        atualizarLocalDateTime();
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Gado getGado() {
        return gado;
    }

    public void setGado(Gado gado) {
        this.gado = gado;
    }

    public StatusLeilao getStatus() {
        return status;
    }

    public void setStatus(StatusLeilao status) {
        this.status = status;
    }

    public Usuario getVencedor() {
        return vencedor;
    }

    public void setVencedor(Usuario vencedor) {
        this.vencedor = vencedor;
    }

    public Leilao toLeilao(){
        Leilao leilao = new Leilao();
        leilao.setCodigo(codigo);
        leilao.setNome(nome);
        leilao.setInitialPrice(initialPrice);
        leilao.setFinalTimestamp(finalDateTime);
        leilao.setUsuario(usuario);
        leilao.setGado(gado);
        leilao.setStatus(status);
        leilao.setAtivo(true);
        return leilao;
    }

    public static LeilaoDTOInput fromLeilao(Leilao leilao){
        LeilaoDTOInput leilaoDTOInput = new LeilaoDTOInput();
        leilaoDTOInput.setCodigo(leilao.getCodigo());
        leilaoDTOInput.setNome(leilao.getNome());
        leilaoDTOInput.setInitialPrice(leilao.getInitialPrice());
        leilaoDTOInput.setFinalDateTime(leilao.getFinalTimestamp());
        if (leilao.getFinalTimestamp() != null) {
            leilaoDTOInput.setDataFinal(leilao.getFinalTimestamp().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            leilaoDTOInput.setHoraFinal(leilao.getFinalTimestamp().format(DateTimeFormatter.ofPattern("HH:mm")));
        }
        leilaoDTOInput.setUsuario(leilao.getUsuario());
        leilaoDTOInput.setGado(leilao.getGado());
        leilaoDTOInput.setStatus(leilao.getStatus());

        return leilaoDTOInput;
    }
    
}
