package com.example.misc.dataloader;

import java.util.Map;
import java.util.UUID;
import java.util.stream.IntStream;

import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.stereotype.Component;

import graphql.schema.idl.RuntimeWiring.Builder;

@Component
public class BatchSizeExampleDataWiring implements RuntimeWiringBuilderCustomizer {

	private static final Logger logger = LoggerFactory.getLogger(BatchSizeExampleDataWiring.class);

	@Override
	public void customize(Builder builder) {
		builder.type("Query",
				b -> b.dataFetcher("batchSizeExample", env -> {
					logger.debug("Fetch batchSizeExample");
					return IntStream.rangeClosed(1, 10).mapToObj(i -> Map.of(
							"id", UUID.randomUUID().toString(),
							"key", UUID.randomUUID().toString()));
				}));
		builder.type("BatchSizeExample",
				b -> b.dataFetcher("value", env -> {
					Map<String, Object> source = env.getSource();
					logger.debug("Fetch batchSizeExample.value: source = {}", source);
					String key = (String) source.get("key");

					DataLoader<String, String> dataLoader = env
							.getDataLoader("BatchSizeExample.value");

					return dataLoader.load(key);
				}));
	}
}
