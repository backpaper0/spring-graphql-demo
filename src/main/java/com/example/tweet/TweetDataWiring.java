package com.example.tweet;

import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.graphql.boot.RuntimeWiringBuilderCustomizer;
import org.springframework.stereotype.Component;

import graphql.schema.idl.RuntimeWiring.Builder;

@Component
public class TweetDataWiring implements RuntimeWiringBuilderCustomizer {

	private static final Logger logger = LoggerFactory.getLogger(TweetDataWiring.class);

	private final TweetRepository tweetRepository;

	public TweetDataWiring(TweetRepository tweetRepository) {
		this.tweetRepository = tweetRepository;
	}

	@Override
	public void customize(Builder builder) {
		builder.type("Query",
				typeRuntimeWiringBuilder -> typeRuntimeWiringBuilder.dataFetcher("timeline",
						env -> {
							String id = env.getArgument("id");

							if (logger.isDebugEnabled()) {
								logger.debug("fetch Timeline: id = {}", id);
							}

							return tweetRepository.findTimelineById(id);
						}));
		builder.type("Tweet",
				typeRuntimeWiringBuilder -> typeRuntimeWiringBuilder.dataFetcher("user", env -> {
					Tweet tweet = env.getSource();
					DataLoader<String, User> dataLoader = env.getDataLoader("tweet.user");
					String userId = tweet.getUserId();

					if (logger.isDebugEnabled()) {
						logger.debug("fetch Tweet.user: userId = {}", userId);
					}

					return dataLoader.load(userId);
				}));
	}
}
