package web.lance_bovino.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.webmvc.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrosController implements ErrorController {
    
    private static final Logger logger = LoggerFactory.getLogger(ErrosController.class);

    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object uriAttribute = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        
        // Evita NullPointerException caso a URI venha nula
        String url = uriAttribute != null ? uriAttribute.toString() : "URL desconhecida";
        String retorno = "error"; // View genérica padrão
        
        if (status != null) {
            // valueOf é mais seguro que o cast (int) direto, pois o Spring pode retornar o status como String em alguns contextos
            HttpStatus httpStatus = HttpStatus.resolve(Integer.valueOf(status.toString()));
            
            if (httpStatus == HttpStatus.NOT_FOUND) {
                logger.warn("A URL {} foi acessada mas não existe.", url);
                retorno = "error/404";
            } else if (httpStatus == HttpStatus.FORBIDDEN) {
                logger.warn("Tentaram acessar a URL {} sem permissão.", url);
                retorno = "error/403";
            } else if (httpStatus == HttpStatus.INTERNAL_SERVER_ERROR) {
                logger.error("Ocorreu um erro interno no servidor ao tentar acessar a URL {}.", url);
                retorno = "error/500";
            }
        }
        
        // Mantém a mágica do HTMX intacta
        return request.getHeader("HX-Request") == null ? retorno : retorno + " :: conteudo";
    }
}