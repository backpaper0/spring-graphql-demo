package com.example.tweet;

import org.dataloader.DataLoader;
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
public class TweetWebInterceptor implements WebInterceptor {

	private static final Logger logger = LoggerFactory
			.getLogger(TweetWebInterceptor.class);

	private final UserLoader loader;

	public TweetWebInterceptor(UserLoader loader) {
		this.loader = loader;
	}

	@Override
	public Mono<WebOutput> intercept(WebInput webInput, WebGraphQlHandler next) {
		webInput.configureExecutionInput((input, builder) -> {
			logger.debug("Register UserLoader");
			DataLoaderRegistry registry = new DataLoaderRegistry();
			if (input.getDataLoaderRegistry() != null) {
				registry = registry.combine(input.getDataLoaderRegistry());
			}
			registry.register("tweet.user", DataLoader.newDataLoader(loader));
			return builder.dataLoaderRegistry(registry).build();
		});
		return next.handle(webInput);
	}
}
