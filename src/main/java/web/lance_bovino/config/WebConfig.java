package web.lance_bovino.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import web.lance_bovino.interceptor.NotificacaoHeaderInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private NotificacaoHeaderInterceptor notificacaoInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(notificacaoInterceptor);
    }
}
