package com.example.tweet;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

@Component
public class TweetRepository {

	private final List<Timeline> timelines = List.of(new Timeline(
			"739d27f4-a946-4117-8efb-1156fdfcb994",
			List.of(
					new Tweet("おなかいたい", "d4036372-69a9-414a-9089-13c5c6d36c0c"),
					new Tweet("ごはんたべた", "d4036372-69a9-414a-9089-13c5c6d36c0c"),
					new Tweet("おなかすいた", "d4036372-69a9-414a-9089-13c5c6d36c0c"),
					new Tweet("ねすぎた", "d4036372-69a9-414a-9089-13c5c6d36c0c"),
					new Tweet("ねます", "d4036372-69a9-414a-9089-13c5c6d36c0c"),
					new Tweet("ねむい", "d4036372-69a9-414a-9089-13c5c6d36c0c"),

					new Tweet("イベント近いのでしばらく浮上しないかも…", "2738a0e3-4d8c-4313-ab98-e2f9ce6e18d4"),
					new Tweet("リリエルリリエルリリエルリリエルリリエルリリエルリリエルリリエル",
							"2738a0e3-4d8c-4313-ab98-e2f9ce6e18d4"))));

	public Optional<Timeline> findTimelineById(String id) {
		return timelines.stream().filter(a -> a.getId().equals(id)).findAny();
	}
}
