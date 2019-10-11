package com.soap.webservices.soapcursespringboot.soap;


import java.util.List;

import javax.management.RuntimeErrorException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.gabriela.courses.CourseDetails;
import com.gabriela.courses.DeleteCourseDetailRequest;
import com.gabriela.courses.DeleteCourseDetailResponse;
import com.gabriela.courses.GetAllCourseDetailRequest;
import com.gabriela.courses.GetAllCourseDetailResponse;
import com.gabriela.courses.GetCourseDetailRequest;
import com.gabriela.courses.GetCourseDetailResponse;
import com.gabriela.courses.Status;
import com.soap.webservices.soapcursespringboot.soap.bean.Course;
import com.soap.webservices.soapcursespringboot.soap.exception.CourseNotFoudException;
import com.soap.webservices.soapcursespringboot.soap.service.CourseDetailsService;

//Indicamos que se envia una solicitud y se recibe una respuesta
@Endpoint
public class CourseDetailsEndpoint {
	
	@Autowired
	CourseDetailsService courseDetailsService;
	
	//method
	//inpunt --- GetCourseDetailRequest
	//output --- GetCourseDetailResponse
	//http://gabriela.com/courses
	
	//Método que acepta la solicitud de obtener detalles del curso como entrada y salida
	
	
	//Anotacion configurada para que permita aceptar solicitudes del name-space  y el nombre esto mediante la configuración y 
	//PayloadRoot
	@PayloadRoot(namespace="http://gabriela.com/courses",localPart="GetCourseDetailRequest")
	//anotacion para conversion de java a Xml una vez que regrese la repsueta
	@ResponsePayload
	public GetCourseDetailResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailRequest request) {

		//etablecer valores de la respuesta
		Course course = courseDetailsService.buscarPorId(request.getId());
		
		//validar curso inexistente 	
		if(course == null) {
			throw new CourseNotFoudException("Id de curso Invalido "+ request.getId());
		}
		
		return mapCourseDetails(course);
		
	}

	
	//Anotacion configurada para que permita aceptar solicitudes del name-space  y el nombre esto mediante la configuración y 
	//PayloadRoot
	@PayloadRoot(namespace="http://gabriela.com/courses",localPart="GetAllCourseDetailRequest")
	//anotacion para conversion de java a Xml una vez que regrese la repsueta
	@ResponsePayload
	public GetAllCourseDetailResponse processAllCourseDetailsRequest(@RequestPayload GetAllCourseDetailRequest request) {

		//etablecer valores de la respuesta
		List<Course> courses = courseDetailsService.buscarTodos();
		
		return mapAllCourseDetails(courses);
		
	}
	
	//Anotacion configurada para que permita aceptar solicitudes del name-space  y el nombre esto mediante la configuración y 
	//PayloadRoot
	@PayloadRoot(namespace="http://gabriela.com/courses",localPart="DeleteCourseDetailRequest")
	//anotacion para conversion de java a Xml una vez que regrese la repsueta
	@ResponsePayload
	public DeleteCourseDetailResponse processDeleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailRequest request) {

		//etablecer valores de la respuesta
		Status  status = courseDetailsService.eliminarPorId(request.getId());
		
		DeleteCourseDetailResponse response = new DeleteCourseDetailResponse();
		response.setStatus(mapStatus(status));
		return response;
		
	}

	private Status mapStatus(Status status) {
		if(status== Status.FAILURE) {
			return Status.FAILURE;
		}else {
			return Status.SUCCESS;
		}
	
	}


	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();

		courseDetails.setId(course.getId());

		courseDetails.setName(course.getName());

		courseDetails.setDecription(course.getDesription());
		return courseDetails;
	}
	

	
	private GetCourseDetailResponse mapCourseDetails(Course course) {
		GetCourseDetailResponse response = new GetCourseDetailResponse();
		response.setCourseDetails(mapCourse(course));
		return response;
	}

	private GetAllCourseDetailResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailResponse response = new GetAllCourseDetailResponse();
		for (Course course : courses) {
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}
		return response;
	}
	
	




	
	
	

}
