package com.soap.webservices.soapcursespringboot.soap.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

//anotacion para el manejo de excepciones
@SoapFault(faultCode=FaultCode.CUSTOM, customFaultCode="{http://gabiela.com/courses}001_COURSE_NOT_FOUND")		
public class CourseNotFoudException extends RuntimeException {
 

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public CourseNotFoudException(String message) {
		super(message);
	}
}
	