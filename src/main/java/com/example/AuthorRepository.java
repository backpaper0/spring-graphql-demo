package com.example;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class AuthorRepository {

	private final List<Author> values = List.of(
			build("659fdc059436a16e405db9112a2f37cb", "鳥山 明"),
			build("0132f3e95811491720e22fdb4768f93e", "冨樫 義博"));

	public Optional<Author> findById(String id) {
		return values.stream().filter(a -> a.getId().equals(id)).findAny();
	}

	private static Author build(String id, String name) {
		var author = new Author();
		author.setId(id);
		author.setName(name);
		return author;
	}
}
