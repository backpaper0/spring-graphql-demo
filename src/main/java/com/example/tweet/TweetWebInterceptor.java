package com.example.tweet;

import org.dataloader.DataLoader;
import org.dataloader.DataLoaderRegistry;
import org.springframework.graphql.web.WebGraphQlHandler;
import org.springframework.graphql.web.WebInput;
import org.springframework.graphql.web.WebInterceptor;
import org.springframework.graphql.web.WebOutput;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class TweetWebInterceptor implements WebInterceptor {

	private final UserLoader userLoader;

	public TweetWebInterceptor(UserLoader userLoader) {
		this.userLoader = userLoader;
	}

	@Override
	public Mono<WebOutput> intercept(WebInput webInput, WebGraphQlHandler next) {
		webInput.configureExecutionInput((input, builder) -> {
			DataLoaderRegistry dataLoaderRegistry = new DataLoaderRegistry();
			dataLoaderRegistry.register("tweet.user", DataLoader.newDataLoader(userLoader));
			return builder.dataLoaderRegistry(dataLoaderRegistry).build();
		});
		return next.handle(webInput);
	}
}
