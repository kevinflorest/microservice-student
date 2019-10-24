package com.sistema.app.controllers;

import java.net.URI;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.support.WebExchangeBindException;
import com.sistema.app.models.documents.Student;
import com.sistema.app.models.services.StudentService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;



@RestController
@RequestMapping("/api/student")
public class StudentController {
	
	@Autowired
	private StudentService service;

	
	@GetMapping
	public Mono<ResponseEntity<Flux<Student>>> listStudent(){
		return Mono.just(
				ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAllStudent()));
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<Student>> StudentById(@PathVariable String id){
		return service.findByIdStudent(id).map(s -> 
		ResponseEntity
		.ok()
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.body(s))
	    .defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@GetMapping("document/{numberDocument}")
	public Mono<ResponseEntity<Student>> StudentBynumberDocument(@PathVariable String numberDocument){
		return service.findByNumberDocument(numberDocument).map(s -> 
		ResponseEntity
		.ok()
		.contentType(MediaType.APPLICATION_JSON_UTF8)
		.body(s))
	    .defaultIfEmpty(ResponseEntity.notFound().build());
	}
	
	@GetMapping("names/{firstName}")
	public Mono<ResponseEntity<Flux<Student>>> StudentByName(@PathVariable String firstName){
		return Mono.just(
				ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON_UTF8)
				.body(service.findAllStudentByName(firstName)));
	}
	
	@PostMapping
	public Mono<ResponseEntity<Map<String, Object>>> saveStudent(@Valid @RequestBody Mono<Student> monoStudent){
		
		Map<String, Object> response = new HashMap<String, Object>();
		
		return monoStudent.flatMap(student -> {
			return service.saveStudent(student).map(s-> {
				response.put("student", s);
				response.put("mensaje", "Estudiante registrado con éxito");
				response.put("timestamp", new Date());
				return ResponseEntity
					.created(URI.create("/api/student/".concat(s.getId())))
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(response);
				});
			
		}).onErrorResume(t -> {
			return Mono.just(t).cast(WebExchangeBindException.class)
					.flatMap(e -> Mono.just(e.getFieldErrors()))
					.flatMapMany(Flux::fromIterable)
					.map(fieldError -> "El campo "+fieldError.getField() + " " + fieldError.getDefaultMessage())
					.collectList()
					.flatMap(list -> {
						response.put("errors", list);
						response.put("timestamp", new Date());
						response.put("status", HttpStatus.BAD_REQUEST.value());
						return Mono.just(ResponseEntity.badRequest().body(response));
					});		
		});
	}
	
	@PutMapping("/{id}")
	public Mono<ResponseEntity<Student>> updateStudent(@RequestBody Student student, @PathVariable String id)
	{
		return service.findByIdStudent(id)
				.flatMap(s -> {
					s.setFirstName(student.getFirstName());
					s.setSecondName(student.getSecondName());
					s.setPaternalSurname(student.getPaternalSurname());
					s.setMaternalSurname(student.getMaternalSurname());
					s.setGenderStudent(student.getGenderStudent());
					s.setBirthDate(student.getBirthDate());
					s.setTypeDocument(student.getTypeDocument());
					s.setNumberDocument(student.getNumberDocument());
					return service.saveStudent(s);
				}).map(s -> ResponseEntity.created(URI.create("/api/student/".concat(s.getId())))
				  .contentType(MediaType.APPLICATION_JSON_UTF8)
				  .body(s))
				.defaultIfEmpty(ResponseEntity.notFound().build());
	}	
	
	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Void>> deleteStudent(@PathVariable String id)
	{
		return service.findByIdStudent(id).flatMap(s -> {
			return service.deleteStudent(s).then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)));		
		}).defaultIfEmpty(new ResponseEntity<Void>(HttpStatus.NO_CONTENT));
	}
	
	   //Like Names
	   @GetMapping("likeName/{firstNames}")
		public Mono<ResponseEntity<Flux<Student>>> viewName(@PathVariable String firstNames){
			return Mono.just(
					ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(service.findByNamesLike(firstNames))
					);
		}
	   
		@GetMapping("birthDate/{birthDate}")
		public Mono<ResponseEntity<Student>> viewDateOfBirth(@PathVariable String birthDate) throws ParseException{
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS Z");
			String f1 = birthDate.split("&&")[0]+" 00:00:00.000 +0000";
			Date from = format.parse(f1);
			Date to = format.parse(birthDate.split("&&")[1]+" 00:00:00.000 +0000");
			System.out.println(format.format(from));
			return service.findByDateOfBirthBetween(from,to).map(p-> ResponseEntity.ok()
					.contentType(MediaType.APPLICATION_JSON_UTF8)
					.body(p))
					.defaultIfEmpty(ResponseEntity.notFound().build());
		}
}
