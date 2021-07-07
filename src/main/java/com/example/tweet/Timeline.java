package com.example.tweet;

import java.util.List;

public class Timeline {

	private String id;
	private List<Tweet> tweets;

	public Timeline(String id, List<Tweet> tweets) {
		this.id = id;
		this.tweets = tweets;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Tweet> getTweets() {
		return tweets;
	}

	public void setTweets(List<Tweet> tweets) {
		this.tweets = tweets;
	}
}
