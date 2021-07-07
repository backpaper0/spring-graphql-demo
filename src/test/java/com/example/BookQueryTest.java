package com.example;

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
}
