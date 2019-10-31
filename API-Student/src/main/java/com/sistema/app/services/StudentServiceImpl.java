package com.sistema.app.services;

import java.util.Collections;
import java.util.Date;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import com.sistema.app.controllers.WebClientController;
import com.sistema.app.dao.StudentDAO;
import com.sistema.app.exception.RequestException;
import com.sistema.app.exception.ResponseStatus;
import com.sistema.app.models.Family;
import com.sistema.app.models.Student;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentDAO sdao;
	
	WebClientController  wclient1;

	private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);
	
	@Override
	public Flux<Student> findAllStudent() {
		return sdao.findAll();
	}

	@Override
	public Mono<Student> findByIdStudent(String id) {
		return sdao.findById(id);
	}

	@Override
	public Mono<Student> saveStudent(Student student) {
		Mono<Student> monoStudent = Mono.just(student);
		
		return monoStudent.flatMap(s1 -> {
			Mono<Student> s2 = sdao.findByNumberDocument(s1.getNumberDocument());
			Mono<Boolean> s3 = s2.hasElement();
			return s3.flatMap(s4 -> {
				if(s4)
				{
					throw new RequestException("El estudiante ya existe");
				}
				else
				{
					return sdao.save(student).flatMap(s5 -> {	
//						wclient1.init();
//						Mono<Family> s6 = wclient1.findFamily(s5.getNumberDocument()).log();
						
						Mono<Family> s6 = WebClient
								.builder()
								.baseUrl("http://localhost:8002/api/family/")
								.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
								.build().get().uri("/document/"+s5.getNumberDocument()).retrieve()
								.bodyToMono(Family.class)
								.log();
						
						Mono<Boolean> s7 = s6.hasElement();	
						
						return s7.map(s8 -> {
						if(s8)
						{
							throw new ResponseStatus("\"El estudiante ha sido registrado, tiene familiares\"");
						}
						else
						{
							throw new ResponseStatus("\"El estudiante ha sido registrado, puede registrarle sus familiares\"");	
						}
					});
				});
			}	
		
	});
		});
	}

	@Override
	public Mono<Void> deleteStudent(Student student) {
		return sdao.delete(student);
	}

	@Override
	public Flux<Student> findAllStudentByName(String firstName) {
		return sdao.findAll(firstName);
	}

	@Override
	public Mono<Student> findByNumberDocument(String numberDocument) {
		return sdao.findByNumberDocument(numberDocument);
	}

	@Override
	public Flux<Student> findByNamesLike(String firstName) {
		return sdao.findByNamesLike(firstName);
	}

	@Override
	public Mono<Student> findByDateOfBirthBetween(Date from, Date to) {
		return sdao.findByDateOfBirthBetween(from, to);
	}



}