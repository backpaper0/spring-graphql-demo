package com.example.tweet;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

@Component
public class UserRepository {

	private final List<User> users = List.of(
			new User("d4036372-69a9-414a-9089-13c5c6d36c0c", "NONOA"),
			new User("2738a0e3-4d8c-4313-ab98-e2f9ce6e18d4", "リリエル中毒"));

	public List<User> findByIds(List<String> userIds) {
		return users.stream().filter(a -> userIds.contains(a.getId())).collect(Collectors.toList());
	}
}
