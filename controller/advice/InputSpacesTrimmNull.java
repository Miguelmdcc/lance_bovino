package web.lance_bovino.controller.advice;

import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.InitBinder;

@ControllerAdvice
public class StringTrimmerAdvice {

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        // 'true' faz com que campos que sejam apenas espaços em branco 
        // sejam convertidos para null automaticamente.
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    } 
}