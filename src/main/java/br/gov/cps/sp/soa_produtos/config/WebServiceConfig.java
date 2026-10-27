package br.gov.cps.sp.soa_produtos.config;

import java.util.List;
import java.util.Properties;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurer;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.wss4j2.Wss4jSecurityInterceptor;
import org.springframework.ws.soap.security.wss4j2.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.server.endpoint.SimpleSoapExceptionResolver;
import org.springframework.ws.soap.server.endpoint.SoapFaultAnnotationExceptionResolver;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

@EnableWs
@Configuration
public class WebServiceConfig implements WsConfigurer {

    @Bean
    public ServletRegistrationBean<MessageDispatcherServlet> messageDispatcherServlet(ApplicationContext context) {
        MessageDispatcherServlet servlet = new MessageDispatcherServlet();
        servlet.setApplicationContext(context);
        servlet.setTransformWsdlLocations(true);
        return new ServletRegistrationBean<>(servlet, "/ws/*");
    }

    @Bean(name = "produtos")
    public DefaultWsdl11Definition produtosWsdl(XsdSchema produtosSchema) {
        DefaultWsdl11Definition wsdl = new DefaultWsdl11Definition();
        wsdl.setPortTypeName("ProdutosPort");
        wsdl.setLocationUri("/ws");
        wsdl.setTargetNamespace("http://cps.sp.gov.br/produtos");
        wsdl.setSchema(produtosSchema);
        return wsdl;
    }

    @Bean
    public XsdSchema produtosSchema() {
        return new SimpleXsdSchema(new ClassPathResource("produtos.xsd"));
    }

    // ================= WS-SECURITY =================

    @Bean
    public SimplePasswordValidationCallbackHandler securityCallbackHandler() {
        SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
        Properties usuarios = new Properties();
        usuarios.setProperty("felipe", "senha123"); // em produção: banco ou variável de ambiente
        handler.setUsers(usuarios);
        return handler;
    }

    @Bean
    public Wss4jSecurityInterceptor securityInterceptor() {
        Wss4jSecurityInterceptor interceptor = new Wss4jSecurityInterceptor();
        interceptor.setValidationActions("UsernameToken");
        interceptor.setValidationCallbackHandler(securityCallbackHandler());
        interceptor.setSecurementActions("NoSecurity"); // a resposta não é assinada
        return interceptor;
    }

    @Override
    public void addInterceptors(List<EndpointInterceptor> interceptors) {
        interceptors.add(securityInterceptor());
    }

    // ================= EXCEPTION -> SOAP FAULT =================

    // Exceptions anotadas com @SoapFault (ValidacaoException, ProdutoNaoEncontradoException)
    @Bean
    public SoapFaultAnnotationExceptionResolver annotationExceptionResolver() {
        SoapFaultAnnotationExceptionResolver resolver = new SoapFaultAnnotationExceptionResolver();
        resolver.setOrder(1);
        return resolver;
    }

    // Qualquer outra exception vira um Fault "Server" genérico, sem stack trace
    @Bean
    public SoapFaultMappingExceptionResolver genericExceptionResolver() {
        SoapFaultMappingExceptionResolver resolver = new SoapFaultMappingExceptionResolver();

        SoapFaultDefinition padrao = new SoapFaultDefinition();
        padrao.setFaultCode(SoapFaultDefinition.SERVER);
        padrao.setFaultStringOrReason("Erro interno do servidor");
        resolver.setDefaultFault(padrao);

        resolver.setOrder(2);
        return resolver;
    }
}