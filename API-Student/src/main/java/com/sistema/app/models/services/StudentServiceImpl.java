package com.sistema.app.models.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sistema.app.models.dao.StudentDAO;
import com.sistema.app.models.documents.Student;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class StudentServiceImpl implements StudentService{

	@Autowired
	private StudentDAO sdao;
	
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
		return sdao.save(student);
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
