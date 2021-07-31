package com.example.misc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
public class BatchSizeExampleQueryTest {

	@Autowired
	private GraphQlTester graphQlTester;

	@Test
	void test() {
		String query = "query BatchSizeExample {" +
				"  batchSizeExample {" +
				"    id" +
				"    value" +
				"  }" +
				"}";

		graphQlTester.query(query)
				.execute()

				.path("batchSizeExample[*].value")
				.entityList(String.class)
				.hasSize(10)
				.matches(values -> values.stream()
						.allMatch(value -> value != null && value.isEmpty() == false));
	}
}
