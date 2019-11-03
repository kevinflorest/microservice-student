package com.sistema.app;

import java.util.Collections;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.sistema.app.models.Student;
import com.sistema.app.services.StudentService;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ApiStudentApplicationTests {

	@Autowired
	private WebTestClient client; 
	
	@Autowired
	private StudentService service;
	
	@Test
	public void listStudent() {
		client.get().uri("/api/student/")
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBodyList(Student.class);
	}
	
	@Test
	public void findByStudent() {
		
		Student student = service.findByIdStudent("asds").block();
		
		client.get().uri("/api/student/{id}", Collections.singletonMap("id", student.getId()))
		.accept(MediaType.APPLICATION_JSON_UTF8)
		.exchange()
		.expectStatus().isOk()
		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
		.expectBodyList(Student.class);
	}
	
	

}
