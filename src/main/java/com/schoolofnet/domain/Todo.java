package com.schoolofnet.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Document(collection = "todo")
public class Todo {

	@Id
	private String id;
	
	@Indexed(name = "name",unique = true)
	private String name;
}
