package com.sistema.app.services;

import java.util.Date;

import com.sistema.app.models.Student;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentService {

	Flux<Student> findAllStudent();
	
	Mono<Student> findByIdStudent(String id);
	
	Mono<Student> saveStudent(Student student);
		
	Mono<Void> deleteStudent(Student student);

	Flux<Student> findAllStudentByName(String firstName);
	
	Mono<Student> findByNumberDocument(String numberDocument);
	
	Flux<Student> findByNamesRegex(String firstName);
	
	Mono<Student> findByDateOfBirthBetween(Date from, Date to);
	

	
}
