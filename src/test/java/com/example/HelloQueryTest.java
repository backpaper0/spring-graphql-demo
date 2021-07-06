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
public class HelloQueryTest {

	@Autowired
	private GraphQlTester graphQlTester;

	@Test
	void hello() {
		String query = "{" +
				"  hello" +
				"}";

		graphQlTester.query(query)
				.execute()
				.path("hello")
				.entity(String.class)
				.isEqualTo("Hello, world!");

	}
}
