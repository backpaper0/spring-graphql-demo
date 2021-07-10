
package com.example.helloworld;

import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.stereotype.Component;

import graphql.schema.idl.RuntimeWiring;

@Component
public class HelloWorldDataWiring implements RuntimeWiringBuilderCustomizer {

	@Override
	public void customize(RuntimeWiring.Builder builder) {
		builder.type("Query", a -> {
			return a.dataFetcher("hello", env -> {
				return "Hello, world!";
			});
		});
	}
}