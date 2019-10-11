package com.soap.webservices.soapcursespringboot.soap.service;

import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;

import org.aspectj.weaver.Iterators;

import com.gabriela.courses.Status;
import com.soap.webservices.soapcursespringboot.soap.bean.Course;


import org.springframework.stereotype.Component;

@Component
public class CourseDetailsService {
	public static List<Course> courses = new ArrayList();
	
	public enum status{
		SUCCESS,FAILURE;
	}
	
	static {
		Course course1 = new Course(1, "Spring", "10 pasos"); 
		courses.add(course1);
		
		Course course2 = new  Course(2, "Spring MVC", "10 pasos");
		courses.add(course2);
		
		Course course3 = new Course(3, "Spring Boot", "10 pasos");
		courses.add(course3);
		
		Course course4 = new Course(4, "Maven", "10 pasos");
		courses.add(course4);
		
	}
	
	public Course buscarPorId(int id) {
		for(Course course: courses) {
			if(course.getId() == id) {
				return course;
			}
		}
		return null;
		
	}
	
	public List<Course> buscarTodos(){
		
		return courses;
	}
	
	public Status eliminarPorId(int id) {
		Iterator<Course> iterador  = courses.iterator();
		while(iterador.hasNext()) {
			Course course = iterador.next();
			if(course.getId() == id ) {
				courses.remove(course);
				return  Status.SUCCESS;
			}
			
		}
		
		return Status.FAILURE ;
	}
	
	
	
	

}
