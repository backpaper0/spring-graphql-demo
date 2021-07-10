package com.example;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.graphql.boot.test.tester.AutoConfigureGraphQlTester;
import org.springframework.graphql.test.tester.GraphQlTester;

import com.example.todo.TaskStatus;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureGraphQlTester
public class TaskQueryTest {

	@Autowired
	private GraphQlTester graphQlTester;

	@Test
	void task() {
		String query = "query tasks($first: Int, $after: String) {" +
				"  tasks(first: $first, after: $after) {" +
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
				.variable("first", 3)
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

		graphQlTester.query(query)
				.variable("first", 3)
				.variable("after", after)
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

	@Test
	void updateTaskStatus() {
		String query = "query HeadTask {" +
				"  tasks(first: 1) {" +
				"    edges {" +
				"      node {" +
				"        id" +
				"        text" +
				"        status" +
				"      }" +
				"    }" +
				"  }" +
				"}";

		String mutation = "mutation UpdateTaskStatus($id: ID!, $status: TaskStatus!) {" +
				"  updateTaskStatus(id: $id, status: $status) {" +
				"    text" +
				"    status" +
				"  }" +
				"}";

		String id = graphQlTester.query(query)
				.execute()

				.path("tasks.edges[*].node.text")
				.entityList(String.class)
				.isEqualTo(List.of("foo"))

				.path("tasks.edges[*].node.status")
				.entityList(TaskStatus.class)
				.isEqualTo(List.of(TaskStatus.TODO))

				.path("tasks.edges[*].node.id")
				.entityList(String.class)
				.get().get(0);

		graphQlTester.query(mutation)
				.variable("id", id)
				.variable("status", TaskStatus.DOING)
				.execute()

				.path("updateTaskStatus.text")
				.entity(String.class)
				.isEqualTo("foo")

				.path("updateTaskStatus.status")
				.entity(TaskStatus.class)
				.isEqualTo(TaskStatus.DOING);

		graphQlTester.query(query)
				.execute()

				.path("tasks.edges[*].node.text")
				.entityList(String.class)
				.isEqualTo(List.of("foo"))

				.path("tasks.edges[*].node.status")
				.entityList(TaskStatus.class)
				.isEqualTo(List.of(TaskStatus.DOING));
	}
}
