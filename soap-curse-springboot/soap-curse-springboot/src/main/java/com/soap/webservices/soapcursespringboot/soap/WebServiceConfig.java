package com.soap.webservices.soapcursespringboot.soap;



import java.util.Collections;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.server.EndpointInterceptor;
import org.springframework.ws.soap.security.xwss.callback.SimplePasswordValidationCallbackHandler;
import org.springframework.ws.soap.security.xwss.XwsSecurityInterceptor;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;





//Tenemos que manejar un servlet para manejar todas las solictudes y asignarle una URL

//Habilitar Spring WebServices
@EnableWs
//Configuración de Spring
@Configuration
public class WebServiceConfig extends  WsConfigurerAdapter {
	//Servlet Despachador de Mensajes
		//Contexto de la aplicación
	//URL -> /ws/*
	
	
	//Se asigna el servlet a la URL --->Mapear el Servlet a la URL
	@Bean
	public ServletRegistrationBean servletDespachadorMensajes(ApplicationContext contexto) {
		MessageDispatcherServlet messageDispatcherServlet = new MessageDispatcherServlet();
		//Configurar Contexto de la aplicación
		messageDispatcherServlet.setApplicationContext(contexto);
		//Transformamiento de las definiciones de  WSDL mediante el Servlet despachador de mensajes
		messageDispatcherServlet.setTransformWsdlLocations(true);
		
		
		return new ServletRegistrationBean(messageDispatcherServlet, "/ws/*");
	}

	
	
	//WSDL
	// /ws/courses.wsdl
	// course-details.xsd
	@Bean(name="courses")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema coursesSchema) {
		DefaultWsdl11Definition definition = new DefaultWsdl11Definition();
		definition.setPortTypeName("CoursePort");
		definition.setTargetNamespace("http://gabriela.com/courses");
		definition.setLocationUri("/ws");
		definition.setSchema(coursesSchema);
		return definition;
	}
	
	@Bean
	public XsdSchema coursesSchema() {
		return  new SimpleXsdSchema(new ClassPathResource("course-details.xsd"));
	}
	
	//implementacion de seguridad
	//1-Configuracion de interceptor (intercepta todas la solicitudes que lleguen del soap y comprueba si es seguro)
	  //1.1 Definir un controlador de devolucion de llamada
	  //1.2 Definit Segurdad Politica
	//2-Agregar dicho interceptor a la lista de interceptores de Spring
	
	@Bean 
	public XwsSecurityInterceptor securityInterceptor() {
		XwsSecurityInterceptor securiryInterceptor = new XwsSecurityInterceptor();
		securiryInterceptor.setCallbackHandler(callbackHandler());
		//configuracion de politica simple
		securiryInterceptor.setPolicyConfiguration(new ClassPathResource("securityPolicy.xml"));
		
		return securiryInterceptor;
		
	}
	
	//creamos controlador de devolucion de llamada con 1 idetificacion de user y pass simples
	@Bean
	public SimplePasswordValidationCallbackHandler callbackHandler(){
		SimplePasswordValidationCallbackHandler handler = new SimplePasswordValidationCallbackHandler();
		// Crear mapa con dos entradas simples con una coleccion singleton de un solo usuario
		handler.setUsersMap(Collections.singletonMap("user", "password"));
		return handler;
		
	}
	
	
	
	//2-Agregar dicho interceptor a la lista de interceptores de Spring
	@Override
	public void addInterceptors(List<EndpointInterceptor> interceptors) {
	      interceptors.add(securityInterceptor());
	}


	
	

}
