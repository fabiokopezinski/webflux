package com.schoolofnet.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.schoolofnet.domain.Todo;

import reactor.core.publisher.Mono;

@Repository
public interface TodoRepository extends ReactiveCrudRepository<Todo,String> {

	Mono<Todo> findByName(String name);
}
