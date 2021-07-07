package com.example;

import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
public class BookQueryTest {

	@Autowired
	private GraphQlTester graphQlTester;

	@Test
	void book() {
		String query = "{" +
				"  book(isbn: \"4-08-851181-6\") {" +
				"    isbn" +
				"    title" +
				"    author {" +
				"      name" +
				"    }" +
				"  }" +
				"}";

		graphQlTester.query(query)
				.execute()

				.path("book.isbn")
				.entity(String.class)
				.isEqualTo("4-08-851181-6")

				.path("book.title")
				.entity(String.class)
				.isEqualTo("Dr.スランプ 1")

				.path("book.author.name")
				.entity(String.class)
				.isEqualTo("鳥山 明");
	}

	// https://graphql.org/learn/queries/#aliases
	@Test
	void aliases() {
		String query = "{" +
				"  book1: book(isbn: \"4-08-851181-6\") {" +
				"    title" +
				"  }" +
				"  book2: book(isbn: \"4-08-851831-4\") {" +
				"    title" +
				"  }" +
				"}";

		graphQlTester.query(query)
				.execute()

				.path("book1")
				.entity(Map.class)
				.isEqualTo(Map.of("title", "Dr.スランプ 1"))

				.path("book2")
				.entity(Map.class)
				.isEqualTo(Map.of("title", "DRAGON BALL 1"));
	}

	// https://graphql.org/learn/queries/#fragments
	@Test
	void fragments() throws Exception {
		String query = "{" +
				"  book1: book(isbn: \"4-08-851181-6\") {" +
				"    ...bookFields" +
				"  }" +
				"  book2: book(isbn: \"4-08-851831-4\") {" +
				"    ...bookFields" +
				"  }" +
				"}" +
				"fragment bookFields on Book {" +
				"  title" +
				"  author {" +
				"    name" +
				"  }" +
				"}";

		graphQlTester.query(query)
				.execute()

				.path("book1")
				.entity(Map.class)
				.isEqualTo(Map.of("title", "Dr.スランプ 1", "author", Map.of("name", "鳥山 明")))

				.path("book2")
				.entity(Map.class)
				.isEqualTo(Map.of("title", "DRAGON BALL 1", "author", Map.of("name", "鳥山 明")));
	}
}
