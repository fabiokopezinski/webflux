package com.schoolofnet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import reactor.core.publisher.Mono;

@RestController
public class HelloController {

	@GetMapping
	@ResponseBody
	public Mono<String> sayHello() {
		return Mono.just("Hello World from Spring WebFlux");
	}
}
