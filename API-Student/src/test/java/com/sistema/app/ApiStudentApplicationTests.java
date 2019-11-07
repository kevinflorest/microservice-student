package com.sistema.app;

import java.util.Collections;
import java.util.List;
import org.assertj.core.api.Assertions;
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
	public void contextLoads() {
	}
	
//	@Test
//	public void listStudent() {
//		client.get().uri("/api/student/")
//		.accept(MediaType.APPLICATION_JSON_UTF8)
//		.exchange()
//		.expectStatus().isOk()
//		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8)
//		.expectBodyList(Student.class).consumeWith(response -> {
//			List<Student> students = response.getResponseBody();
//			
//			students.forEach(p -> {
//				System.out.println(p.getId());
//			});
//			
//			Assertions.assertThat(students.size()>0).isTrue();
//		});;
//	}
//	
//	@Test
//	public void findByStudent() {
//		
//		Student student = service.findByIdStudent("5dba014cd75cf84d6c018f97").block();
//		
//		client.get().uri("/api/student/{id}", Collections.singletonMap("id", student.getId()))
//		.accept(MediaType.APPLICATION_JSON_UTF8)
//		.exchange()
//		.expectStatus().isOk()
//		.expectHeader().contentType(MediaType.APPLICATION_JSON_UTF8);
//		
//		
//	}
	
	

}
