package com.example.tweet;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.dataloader.BatchLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserLoader implements BatchLoader<String, User> {

	private static final Logger logger = LoggerFactory.getLogger(UserLoader.class);

	private final UserRepository userRepository;

	public UserLoader(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public CompletionStage<List<User>> load(List<String> userIds) {

		if (logger.isDebugEnabled()) {
			logger.debug("load users: userIds = {}", userIds);
		}

		List<User> users = userRepository.findByIds(userIds);
		return CompletableFuture.completedStage(users);
	}
}
