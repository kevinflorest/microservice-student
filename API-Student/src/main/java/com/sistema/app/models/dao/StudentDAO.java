package com.sistema.app.models.dao;

import java.util.Date;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.sistema.app.models.documents.Student;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface StudentDAO extends ReactiveMongoRepository<Student, String> {

	@Query("{ 'firstName' : ?0}")
	Flux<Student> findAll(String firstName);
	
	@Query("{ 'numberDocument' : ?0}")
	Mono<Student> findByNumberDocument(String numberDocument);
	
	@Query("{'firstName' : {$regex : ?0 } }")
	Flux<Student> findByNamesLike(String firstName);
	
	@Query("{'dateOfBirth' : {'$gt' : ?0, '$lt' : ?1}}")
	Mono<Student> findByDateOfBirthBetween(Date from, Date to);
}

