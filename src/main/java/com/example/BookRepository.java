package com.example;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class BookRepository {

	private final List<Book> values = List.of(
			build("4-08-851831-4", "DRAGON BALL 1", "659fdc059436a16e405db9112a2f37cb"),
			build("4-08-851181-6", "Dr.スランプ 1", "659fdc059436a16e405db9112a2f37cb"),
			build("4-08-871273-0", "幽★遊★白書 1", "0132f3e95811491720e22fdb4768f93e"));

	public Optional<Book> findByIsbn(String isbn) {
		return values.stream().filter(a -> a.getIsbn().equals(isbn)).findAny();
	}

	private static Book build(String isbn, String title, String authorId) {
		var book = new Book();
		book.setIsbn(isbn);
		book.setTitle(title);
		book.setAuthorId(authorId);
		return book;
	}
}
