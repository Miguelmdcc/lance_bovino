package web.lance_bovino.controller.advice;

import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAllExceptions(HttpServletRequest request, Exception exception) {
        
        HttpStatusCode statusCode = (exception instanceof ErrorResponse) ? ((ErrorResponse) exception).getStatusCode()
                : HttpStatus.INTERNAL_SERVER_ERROR;
        
        logger.error("A requisição {} lançou uma {}, com a falha de código {}", request.getRequestURL(), exception, statusCode);
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        logger.error("Stack trace da exceção: {}", sw.toString());

        // --- AQUI ESTÁ A IMPLEMENTAÇÃO ---
        ModelAndView mav = new ModelAndView();
        
        // Define o fragmento se for HTMX, ou a view completa se for navegação normal
        mav.setViewName(request.getHeader("HX-Request") != null ? "error :: conteudo" : "error");
        
        // Define o status HTTP real (500, etc) para o navegador
        mav.setStatus(statusCode);
        
        // Adiciona a exceção ao modelo para poder exibir mensagens amigáveis no HTML
        mav.addObject("exception", exception); 
        
        return mav;
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public String handleError404(HttpServletRequest request, Exception exception) throws Exception {
        throw exception;
    }
}