package com.schoolofnet.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.schoolofnet.domain.Todo;
import com.schoolofnet.repository.TodoRepository;
import com.sun.jdi.request.DuplicateRequestException;

import javassist.NotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/todos")
@AllArgsConstructor
@Slf4j
public class TodoController {

	private TodoRepository todoRepository;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	public Mono<Todo> save(@RequestBody Todo todo) {
		log.info("method=save name={}", todo.getName());
		return todoRepository.findByName(todo.getName())
				.flatMap(existing -> Mono.error(new DuplicateRequestException("Já existe")))
				.switchIfEmpty(Mono.defer(() -> todoRepository.save(todo))).cast(Todo.class);
	}

	@GetMapping
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Flux<Todo> findAll() {
		log.info("method=findAll ");
		return todoRepository.findAll();
	}

	@GetMapping("/{id}")
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public Mono<Todo> findById(@PathVariable("id") String id) {
		log.info("method=findById id={}", id);
		return todoRepository.findByName(id).switchIfEmpty(Mono.error(new NotFoundException("Não encontrado")));
	}

	@DeleteMapping("/{id}")
	public Mono<ResponseEntity<Object>> delete(@PathVariable("id") String id) {
		return todoRepository.findById(id)
				.flatMap(existing -> todoRepository.deleteById(id).then(Mono.just(ResponseEntity.noContent().build())))
				.switchIfEmpty(Mono.error(new NotFoundException("Não encontrado")));
	}

}
