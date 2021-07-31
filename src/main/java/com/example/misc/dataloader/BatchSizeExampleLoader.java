package com.example.misc.dataloader;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

import org.dataloader.BatchLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class BatchSizeExampleLoader implements BatchLoader<String, String> {

	private static final Logger logger = LoggerFactory.getLogger(BatchSizeExampleLoader.class);

	@Override
	public CompletionStage<List<String>> load(List<String> keys) {

		logger.debug("load: size = {}, keys = {}", keys.size(), keys);

		return CompletableFuture.completedStage(keys);
	}
}
