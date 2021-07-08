package com.example;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
public class TaskQueryTest {

	@Autowired
	private GraphQlTester graphQlTester;

	@Test
	void task() {
		String query = "{" +
				"  tasks(first: 3) {" +
				"    edges {" +
				"      node {" +
				"        text" +
				"      }" +
				"    }" +
				"    pageInfo {" +
				"      hasNextPage" +
				"      endCursor" +
				"    }" +
				"  }" +
				"}";

		String after = graphQlTester.query(query)
				.execute()

				.path("tasks.edges[*].node.text")
				.entityList(String.class)
				.isEqualTo(List.of("foo", "bar", "baz"))

				.path("tasks.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(true)

				.path("tasks.pageInfo.endCursor")
				.valueIsNotEmpty()
				.entity(String.class)
				.get();

		String query2 = "{" +
				"  tasks(first: 3, after: \"" + after + "\") {" +
				"    edges {" +
				"      node {" +
				"        text" +
				"      }" +
				"    }" +
				"    pageInfo {" +
				"      hasNextPage" +
				"      endCursor" +
				"    }" +
				"  }" +
				"}";

		graphQlTester.query(query2)
				.execute()

				.path("tasks.edges[*].node.text")
				.entityList(String.class)
				.isEqualTo(List.of("qux", "quux"))

				.path("tasks.pageInfo.hasNextPage")
				.entity(boolean.class)
				.isEqualTo(false)

				.path("tasks.pageInfo.endCursor")
				.valueIsEmpty();
	}
}