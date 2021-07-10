
package com.example.todo;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.stereotype.Component;

import graphql.relay.Connection;
import graphql.relay.ConnectionCursor;
import graphql.relay.DefaultConnection;
import graphql.relay.DefaultConnectionCursor;
import graphql.relay.DefaultEdge;
import graphql.relay.DefaultPageInfo;
import graphql.relay.Edge;
import graphql.relay.PageInfo;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.GraphQLScalarType;
import graphql.schema.idl.RuntimeWiring;

@Component
public class TaskDataWiring implements RuntimeWiringBuilderCustomizer {

	private final TaskRepository taskRepository;

	public TaskDataWiring(TaskRepository taskRepository) {
		this.taskRepository = taskRepository;
	}

	@Override
	public void customize(RuntimeWiring.Builder builder) {
		builder.type("Query",
				typeRuntimeWiringBuilder -> typeRuntimeWiringBuilder.dataFetcher("tasks",
						new DataFetcherImpl()));
		builder.type("Mutation",
				typeRuntimeWiringBuilder -> typeRuntimeWiringBuilder.dataFetcher("newTask",
						env -> taskRepository.newTask(env.getArgument("text"))));
		builder.scalar(GraphQLScalarType.newScalar().name("TaskText")
				.coercing(new TaskTextCoercing())
				.build());
	}

	private class DataFetcherImpl implements DataFetcher<Connection<Task>> {

		@Override
		public Connection<Task> get(DataFetchingEnvironment environment) throws Exception {

			// https://graphql.org/learn/pagination/
			// https://relay.dev/graphql/connections.htm

			int first = environment.getArgument("first");
			String after = environment.getArgument("after");
			List<Task> tasks = taskRepository.find(first + 1, after);

			boolean hasNextPage = tasks.size() > first;

			List<Task> ts;
			if (hasNextPage) {
				ts = tasks.subList(0, first);
			} else {
				ts = tasks;
			}

			List<Edge<Task>> edges = ts.stream()
					.map(node -> new DefaultEdge<>(node, new DefaultConnectionCursor(node.getId())))
					.collect(Collectors.toList());

			ConnectionCursor startCursor;
			ConnectionCursor endCursor;
			if (ts.isEmpty()) {
				startCursor = null;
				endCursor = null;
			} else {
				startCursor = new DefaultConnectionCursor(ts.get(0).getId());
				endCursor = new DefaultConnectionCursor(ts.get(ts.size() - 1).getId());
			}
			boolean hasPreviousPage = false;
			PageInfo pageInfo = new DefaultPageInfo(startCursor, endCursor, hasPreviousPage,
					hasNextPage);

			return new DefaultConnection<>(edges, pageInfo);
		}
	}
}