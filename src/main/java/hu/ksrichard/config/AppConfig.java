package hu.ksrichard.config;

import hu.ksrichard.converter.StringToMessageConverter;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

/**
 * Basic application configuration
 * It defines common beans for embedded application
 * @author ksrichard
 */
@Configuration
public class AppConfig extends WebMvcConfigurerAdapter {

    /**
     * View Resolver bean
     * @return {@link InternalResourceViewResolver}
     */
    @Bean
    public InternalResourceViewResolver views(){
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/views/");
        viewResolver.setSuffix(".jsp");
        return viewResolver;
    }

    /**
     * Embedded Servlet Container Customizer bean
     * Setup custom error pages
     * @return {@link EmbeddedServletContainerCustomizer}
     */
    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return new EmbeddedServletContainerCustomizer() {
            @Override
            public void customize(ConfigurableEmbeddedServletContainer container) {
                ErrorPage error400Page = new ErrorPage(HttpStatus.BAD_REQUEST, "/400");
                ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/404");
                ErrorPage error405Page = new ErrorPage(HttpStatus.METHOD_NOT_ALLOWED, "/405");
                ErrorPage error500Page = new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500");
                container.addErrorPages(error404Page, error400Page, error405Page, error500Page);
            }
        };
    }

    /**
     * Add custom converter to application
     * @param registry Class to register converters, formatters etc..
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToMessageConverter());
    }

}
