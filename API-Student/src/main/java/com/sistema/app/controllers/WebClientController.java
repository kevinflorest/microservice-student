package com.sistema.app.controllers;


import javax.annotation.PostConstruct;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.sistema.app.models.Family;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/api/student")
public class WebClientController {
	
	WebClient webClient;
	
	@PostConstruct
	 public void init() { 
			 webClient = WebClient 
			.builder()
			.baseUrl("http://localhost:8002/api/family/")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_UTF8_VALUE)
			.build(); 
			 
	}
	 
   @GetMapping("/family/{idStudent}")
   public Flux<Family> getFamilyList(@PathVariable String idStudent) 
   { 
		return webClient.get().uri("/idStudent/"+idStudent).retrieve().bodyToFlux(Family.class); 
   }
   
//   @GetMapping("/documentFamily/{numberDocument}")
//   public Mono<Family> findFamily(@PathVariable String numberDocument) 
//   { 
//		return webClient.get().uri("/document/"+numberDocument).retrieve().bodyToMono(Family.class); 
//   }

}
