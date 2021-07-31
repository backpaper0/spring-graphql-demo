package com.example.misc.dataloader;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderOptions;
import org.dataloader.DataLoaderRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.web.WebGraphQlHandler;
import org.springframework.graphql.web.WebInput;
import org.springframework.graphql.web.WebInterceptor;
import org.springframework.graphql.web.WebOutput;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class BatchSizeExampleWebInterceptor implements WebInterceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(BatchSizeExampleWebInterceptor.class);

	private final BatchSizeExampleLoader loader;

	public BatchSizeExampleWebInterceptor(BatchSizeExampleLoader loader) {
		this.loader = loader;
	}

	@Override
	public Mono<WebOutput> intercept(WebInput webInput, WebGraphQlHandler next) {
		webInput.configureExecutionInput((input, builder) -> {
			logger.debug("Register BatchSizeExampleLoader");
			DataLoaderRegistry registry = new DataLoaderRegistry();
			if (input.getDataLoaderRegistry() != null) {
				registry = registry.combine(input.getDataLoaderRegistry());
			}
			DataLoaderOptions options = DataLoaderOptions.newOptions().setMaxBatchSize(3);
			registry.register("BatchSizeExample.value", DataLoader.newDataLoader(loader, options));
			return builder.dataLoaderRegistry(registry).build();
		});
		return next.handle(webInput);
	}
}
